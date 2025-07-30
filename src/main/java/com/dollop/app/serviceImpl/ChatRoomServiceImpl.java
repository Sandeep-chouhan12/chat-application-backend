package com.dollop.app.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.dollop.app.constant.MessageConstants;
import com.dollop.app.entity.ChatRoom;
import com.dollop.app.entity.RoomMembers;
import com.dollop.app.entity.User;
import com.dollop.app.exceptions.ResourceNotFound;
import com.dollop.app.module.SocketModule;
import com.dollop.app.repository.IChatRoomRepository;
import com.dollop.app.request.AddMemberRequest;
import com.dollop.app.request.ChatRoomRequest;
import com.dollop.app.request.RemoveMemberPayload;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.response.ChatRoomResponse;
import com.dollop.app.response.LastMessageResponse;
import com.dollop.app.response.RoomMessageResponse;
import com.dollop.app.service.IChatRoomService;
import com.dollop.app.service.IMessageService;
import com.dollop.app.service.IRoomMembersService;
import com.dollop.app.service.IUserService;
import com.dollop.app.utils.AppUtils;
import com.dollop.app.utils.JwtUtils;

@Service
public class ChatRoomServiceImpl implements IChatRoomService {

	@Autowired
	private IChatRoomRepository chatRoomRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AppUtils appUtils;
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoomMembersService roomMembersService;
	@Autowired
	@Lazy
	private SocketModule module;
	@Autowired
	@Lazy
	private IMessageService messageService;

	@Override
	public ApiResponse createRoom(ChatRoomRequest roomRequest) {
		System.err.println("roomRequest ==== > " + roomRequest);
		if (!roomRequest.isGroup()) {
			Optional<ChatRoom> chatRoom = chatRoomRepository.findOneToOneChatRoomBetween(
					roomRequest.getMemberIds().get(0), userService.findCurrentUserById().getId());

			System.err.println("User Already have a room  ==== > " + chatRoom.isPresent());

			if (chatRoom != null && chatRoom.isPresent()) {
				System.err.println("User Already have a room  ==== > " + chatRoom.isPresent());
				return ApiResponse.builder().message("Already found chat Room")
						.response(Map.of("chatRoom", chatRoom.get())).build();
			}
		}
		User currentUser = userService.findCurrentUserById();
		ChatRoom chatRoom = ChatRoom.builder().roomName(roomRequest.getRoomName()).isGroup(roomRequest.isGroup())
				.createdBy(currentUser).build();
		List<RoomMembers> memberList = new ArrayList<>();
		memberList.add(RoomMembers.builder().user(currentUser).chatRoom(chatRoom).isAdmin(true).build());
		for (String id : roomRequest.getMemberIds()) {

			User user = userService.findUserById(id);
			RoomMembers roomMembers = RoomMembers.builder().chatRoom(chatRoom).user(user).build();
			memberList.add(roomMembers);
		}
		for (RoomMembers member : memberList) {
			int i = 0;
			System.err.println(i++ + " === >" + member);
		}
		chatRoom.setMembers(memberList);
		chatRoomRepository.save(chatRoom);

		module.createRoom(chatRoomToChatRoomResponse(chatRoom));
		return ApiResponse.builder().message("chat room create successfully !").build();
	}

	@Override
	public ApiResponse getRoomById(String id) {
		ChatRoom chatRoom = findRoomById(id);

		return ApiResponse.builder().message("single chat room")
				.response(Map.of("chatRoomResponse", chatRoomToChatRoomResponse(chatRoom))).build();
	}

	@Override
	public ChatRoom findRoomById(String id) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound(MessageConstants.ROOM_NOT_FOUND));
		return chatRoom;
	}

	@Override
	public ApiResponse getAllRooms() {
		User currrentUser = userService.findCurrentUserById();
//		List<ChatRoom> allChatRooms = chatRoomRepository.findByMembers_User_Id(currrentUser.getId());
		List<ChatRoom> allChatRooms =chatRoomRepository.findSortedRooms(currrentUser.getId());
		if (allChatRooms == null || allChatRooms.isEmpty())
			throw new ResourceNotFound(MessageConstants.ROOM_NOT_FOUND);

		
		List<ChatRoomResponse> chatRoomResponse = chatRoomToChatRoomResponse(allChatRooms);
		return ApiResponse.builder().message("All Chat Rooms").response(Map.of("chatRoomResponse", chatRoomResponse))
				.build();
	}
	

	
	

	@Override
	public ApiResponse addRoomMember(AddMemberRequest addMemberRequest) {
		ChatRoom chatRoom = findRoomById(addMemberRequest.getRoomId());
		System.err.println("room id == > " + addMemberRequest.getRoomId());
		if (!chatRoom.isGroup())
			throw new ResourceNotFound(MessageConstants.ROOM_NOT_FOUND);
	
		for (String userId : addMemberRequest.getUserIds()) {

			User user = userService.findUserById(userId);
   
			if (roomMembersService.isUserMemberOfChatRoom(addMemberRequest.getRoomId(), userId)) {
				RoomMembers memberOfChatRoomByUserId = roomMembersService
						.getMemberOfChatRoomByUserId(addMemberRequest.getRoomId(), userId);
				memberOfChatRoomByUserId.setIsAvailableInRoom(true);
				roomMembersService.updateIsMemberAvailability(memberOfChatRoomByUserId);

//				server.getRoomOperations(addMemberRequest.getRoomId()).getClients()
//				.forEach(client -> client.sendEvent("add_members_in_group",addMemberRequest.getRoomId()));
//
//				return ApiResponse.builder().message("Member added to group succcessfully !").build();

				// throw new ResourceNotFound(MessageConstants.ALREADY_EXISTS_IN_GROUP);
			} else {
				RoomMembers roomMember = RoomMembers.builder().user(user).chatRoom(chatRoom).isAdmin(false).build();
				chatRoom.getMembers().add(roomMember);
				chatRoom = chatRoomRepository.save(chatRoom);
			}
		}
		module.addMemberInRoom(chatRoomToChatRoomResponse(findRoomById(addMemberRequest.getRoomId()))); // this is an event in socket module
		
		return ApiResponse.builder().message("Member added to group succcessfully !").build();
	}

	@Override
	public ApiResponse removeRoomMember(String id, List<String> memberIds) {
		List<String> removedEmails = new ArrayList<>();
		RemoveMemberPayload payload = RemoveMemberPayload.builder().roomId(id).memberIds(memberIds).build();
	
		for (String memberId : memberIds) {

			RoomMembers roomMember = roomMembersService.findById(memberId);
			removedEmails.add(roomMember.getUser().getEmail());
			roomMember.setIsAvailableInRoom(false);
			roomMembersService.updateIsMemberAvailability(roomMember);
		}
		
		module.removeMemberFromGroup(id , payload); // this is an event in socket module
		
//		for (SocketIOClient client : server.getAllClients()) {
//			String email = client.get("email");
//
//			// If client matches removed member
//			if (email != null && removedEmails.contains(email)) {
//				client.leaveRoom(id); // 👈 force leave
//			}
//		}
		return ApiResponse.builder().message("member removed successfully!").build();

	}

	@Override
	public ApiResponse getRoomsForUser(String userId) {
		userService.findUserById(userId);
		List<ChatRoom> chatRooms = chatRoomRepository.findByMembers_User_Id(userId);
		return ApiResponse.builder().message("Chat Rooms List!")
				.response(Map.of("chatRooms", chatRoomToChatRoomResponse(chatRooms))).build();

	}

	@Override
	public ApiResponse updateRoomName(String id, String newName) {
		ChatRoom chatRoom = findRoomById(id);
		chatRoom.setRoomName(newName);
		chatRoomRepository.save(chatRoom);
		return ApiResponse.builder().message("chat room updated successfully !").build();
	}

	@Override
	public List<ChatRoomResponse> chatRoomToChatRoomResponse(List<ChatRoom> chatRooms) {
		return chatRooms.stream().map(this::chatRoomToChatRoomResponse).toList();

	}

	@Override
	public ChatRoomResponse chatRoomToChatRoomResponse(ChatRoom chatRoom) {
		LastMessageResponse lastMessage = messageService.getLastMessageOfRoom(chatRoom.getId());
		Integer unSeenMessageCount = messageService.getUnSeenMessageCountByRoomId(chatRoom.getId());
		System.err.println(" unSeenMsg count ==== >> >> >> >> > > " +unSeenMessageCount);
		return ChatRoomResponse.builder().id(chatRoom.getId()).isGroup(chatRoom.isGroup()) // or chatRoom.getIsGroup()
																							// depending on your getter
				.roomName(chatRoom.getRoomName()).createdBy(userService.userToUserResponse(chatRoom.getCreatedBy()))
				.members(roomMembersService.roomMemberToRoomMemberResponse(chatRoom.getMembers())).lastMessage(lastMessage).unSeenMessageCount(unSeenMessageCount).build();
	}

}
