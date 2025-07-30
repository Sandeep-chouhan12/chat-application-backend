package com.dollop.app.module;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.dollop.app.entity.ChatRoom;
import com.dollop.app.request.DeleteMessageRequest;
import com.dollop.app.request.JoinRoomRequest;
import com.dollop.app.request.LeaveRoomRequest;
import com.dollop.app.request.MessageStatusUpdateRequest;
import com.dollop.app.request.RemoveMemberPayload;
import com.dollop.app.request.RoomMessageRequest;
import com.dollop.app.request.TypingStatusRequest;
import com.dollop.app.response.ChatRoomResponse;
import com.dollop.app.response.MessageStatusResponse;
import com.dollop.app.response.RoomMembersResponse;
import com.dollop.app.response.RoomMessageResponse;
import com.dollop.app.response.UserResponse;
import com.dollop.app.service.IChatRoomService;
import com.dollop.app.service.IMessageService;
import com.dollop.app.service.IUserService;
import com.dollop.app.utils.JwtUtils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketModule {

	private SocketIOServer server;
	private IMessageService messageService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private OnlineUserTracker onlineUserTracker;
	@Autowired
	private IUserService userService;
    @Autowired
	private IChatRoomService chatRoomService;
	public SocketIOServer getSocketServer() {
		return this.server;
	}

	public SocketModule(SocketIOServer server, IMessageService messageService) {
		this.server = server;
		this.messageService = messageService;

		server.addConnectListener(onConnected());
		server.addDisconnectListener(onDisconnected());
		server.addEventListener("send_message", RoomMessageRequest.class, onChatReceived());
		server.addEventListener("join_room", JoinRoomRequest.class, onJoinRoom());
		server.addEventListener("update_status", MessageStatusUpdateRequest.class, onStatusUpdate());
		server.addEventListener("leave_room", LeaveRoomRequest.class, onLeaveRoom());
		server.addEventListener("delete_message", DeleteMessageRequest.class, onDeleteMessage());
		server.addEventListener("typing_status", TypingStatusRequest.class, onTypingStatus());

	}

	private DataListener<TypingStatusRequest> onTypingStatus() {
		return (client, data, ackSender) -> {
			Set<String> onlineUsers = onlineUserTracker.getOnlineUsers();

			List<RoomMembersResponse> targetMembers = data.getChatRoomResponses().getMembers().stream()
					.filter(m -> !m.getUser().getEmail().equals(data.getCurrentUser().getEmail())&& m.getIsAvailableInRoom()) // exclude sender
					.filter(m -> onlineUsers.contains(m.getUser().getEmail())) // only online users
					.toList();

			targetMembers.forEach(member -> {
				String email = member.getUser().getEmail();
				System.err.println("📡 Sending typing-status to online room member: " + email);
				server.getRoomOperations(email).getClients().forEach(c -> c.sendEvent("typing-status", data));
			});
		};
	}

	private DataListener<DeleteMessageRequest> onDeleteMessage() {
		return (client, data, ackSender) -> {
			log.info("📥 Received delete messages : {}", data);
			List<RoomMessageResponse> deleteMessageForEveryOne = messageService
					.deleteMessageForEveryOne(data.getMessageIds());
			System.err.println("deleted messages === > " + deleteMessageForEveryOne.get(0));
			for (SocketIOClient connectedClient : server.getAllClients()) {
				connectedClient.sendEvent("receive_deleted_messages", deleteMessageForEveryOne);
				connectedClient.sendEvent("last-message", deleteMessageForEveryOne);
				
				;
			}
			
		};
	}

	private DataListener<LeaveRoomRequest> onLeaveRoom() {
		return (client, data, ackSender) -> {
			String roomId = data.getRoomId();
			client.leaveRoom(roomId);
			log.info("🚪 Client {} left room {}", client.getSessionId(), roomId);
		};
	}

	private DataListener<MessageStatusUpdateRequest> onStatusUpdate() {
		return (client, data, ackSender) -> {
			log.info("📥 Received status update: {}", data);
			System.err.println("update status event ...........");
			MessageStatusResponse updateMessageStatus = messageService.updateMessageStatus(data, server);
			if(updateMessageStatus != null) {
			for (SocketIOClient connectedClient : server.getAllClients()) {
				String email = connectedClient.get("email");
				if (email != null && email.equals(data.getSenderEmail())) {
					System.err.println("-------------> Msg emitting to the sender ===== > " + email);
					connectedClient.sendEvent("status_update", Map.of("updateMessageStatus",updateMessageStatus,"messageId" ,data.getMessageId()));
				}
			}
			}
			server.getRoomOperations(data.getReceiverEmail()).getClients().forEach(c -> {
				 c.sendEvent("last-message", data.getMessageId());
			});
		};

	}

	private DataListener<RoomMessageRequest> onChatReceived() {
		return new DataListener<RoomMessageRequest>() {
			@Override
			public void onData(SocketIOClient senderClient, RoomMessageRequest data, AckRequest ackSender)
					throws Exception {
				log.info("🔥 Socket Message Received: {}", data);
				String email = senderClient.get("email"); // ✅ Get user ID stored earlier

				String room = data.getChatRoomId();
				RoomMessageResponse sendMessage = messageService.sendMessage(email, data);
				for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
                    System.err.println(" .......................loop for send msg event ==== > " +client.get("email"));
					client.sendEvent("get_message", sendMessage); // send response DTO

				}
				
				ChatRoom chatRoom = chatRoomService.findRoomById(room);
				List<String> list = chatRoom.getMembers().stream().map(m -> m.getUser().getEmail()).toList();
				System.err.println("List of emails  of users in room == > " +list);
				chatRoom.getMembers().stream().filter(m -> m.getIsAvailableInRoom()).map(m -> m.getUser().getEmail()).forEach(e -> server.getRoomOperations(e)
						.getClients().forEach(c -> c.sendEvent("last-message", sendMessage)));
				;
			}
		};
	}

	private DataListener<JoinRoomRequest> onJoinRoom() {
		return (client, data, ackSender) -> {
			String email = client.get("email");
			String roomId = data.getRoomId();
			log.info("🟢 Received join_room for room ID: {}", roomId);

			if (roomId != null && !roomId.isEmpty()) {
				client.joinRoom(roomId);
				log.info("✅ [{}] joined room: {}", email, roomId);
			} else {
				log.warn("⚠️ No room ID provided by [{}]", email);
			}
		};
	}

	private ConnectListener onConnected() {
		return (client) -> {
			String token = client.getHandshakeData().getSingleUrlParam("token");

			log.warn("🔑 Received token: {}", token);

			if (token == null || token.isEmpty()) {
				client.disconnect();
				log.warn("❌ No token provided in socket connection.");
				return;
			}

			try {
				String email = jwtUtils.extractUsername(token);
				log.info("✅ Authenticated user from token: {}", email);
				client.joinRoom(email);
				client.set("email", email); // store in metadata

				onlineUserTracker.addUser(email);

				broadcastOnlineUsers();
				messageService.markStatusDeliveredByReceiverEmail(email, server);

			} catch (Exception e) {
				client.disconnect();
				log.error("❌ Invalid token during socket connection.", e);
			}

			log.info("Socket ID [{}] connected. Rooms: {}", client.getSessionId(), client.getAllRooms());
		};
	}

	private DisconnectListener onDisconnected() {
		return client -> {
			String email = client.get("email");
			if (email != null) {
				onlineUserTracker.removeUser(email); // ✅ Mark user offline
				log.info("User [{}] marked as offline", email);
				UserResponse updatedUserResponse = userService.updateUserLastSeen(email);
				updateLastSeenEvent(updatedUserResponse);
				broadcastOnlineUsers();
			}

			log.info("Client[{}] - Disconnected from socket", client.getSessionId());
		};
	}

	private void updateLastSeenEvent(UserResponse updatedUserResponse) {
		System.err.println(" update Last seen event run ... >>>>> " + updatedUserResponse);
		Set<String> onlineUsersEmail = onlineUserTracker.getOnlineUsers();
		System.err.println(" online emails ----- > " + onlineUsersEmail);
		onlineUsersEmail.forEach(e -> server.getRoomOperations(e).getClients()
				.forEach(c -> c.sendEvent("update-last-seen", updatedUserResponse)));
	}

	private void broadcastOnlineUsers() {
		for (SocketIOClient c : server.getAllClients()) {
			c.sendEvent("onlineUser", onlineUserTracker.getOnlineUsers());
		}
	}

	public void createRoom(ChatRoomResponse chatRoom) {
		List<String> emails = chatRoom.getMembers().stream().map(m -> m.getUser().getEmail()).toList();
		System.err.println("create chat room event run ... ======== > " + emails);
		emails.forEach(e -> {
			server.getRoomOperations(e).getClients().forEach(c -> {
				System.err.println(c.getAllRooms() + "    ============>>> " + e);
				c.sendEvent("create-chat-room", chatRoom);
			});
		});
	}

	public void addMemberInRoom(ChatRoomResponse chatRoom) {
		List<String> emails = chatRoom.getMembers().stream().filter(m -> m.getIsAvailableInRoom()) // Only include
																									// available members
				.map(m -> m.getUser().getEmail()).toList();
		System.err.println("add room member event run ... ======== > " + emails);
		emails.forEach(e -> {

			server.getRoomOperations(e).getClients().forEach(c -> {
				System.err.println(c.getAllRooms() + "    ============>>> " + e);
				c.sendEvent("add_members_in_group", chatRoom);
			});
		});

	}

	public void removeMemberFromGroup(String roomId, RemoveMemberPayload payload) {

		server.getRoomOperations(roomId).getClients()
				.forEach(client -> client.sendEvent("receive_remove_members", payload));

	}

	public void deleteForMe(String email) {
		server.getRoomOperations(email).getClients().forEach(c-> c.sendEvent("last-message", email));
		
	}
}
