package com.dollop.app.serviceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.dollop.app.constant.MessageConstants;
import com.dollop.app.entity.ChatRoom;
import com.dollop.app.entity.Message;
import com.dollop.app.entity.MessageStatus;
import com.dollop.app.entity.MessageVisibility;
import com.dollop.app.entity.RoomMembers;
import com.dollop.app.entity.User;
import com.dollop.app.enums.MessageType;
import com.dollop.app.enums.ServerType;
import com.dollop.app.exceptions.ResourceNotFound;
import com.dollop.app.module.OnlineUserTracker;
import com.dollop.app.module.SocketModule;
import com.dollop.app.repository.IMessageRepository;
import com.dollop.app.repository.IMessageStatusRepository;
import com.dollop.app.repository.IMessageVisibilityRepository;
import com.dollop.app.request.MessageStatusUpdateRequest;
import com.dollop.app.request.RoomMessageRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.response.LastMessageResponse;
import com.dollop.app.response.MessageStatusResponse;
import com.dollop.app.response.RoomMessageResponse;
import com.dollop.app.response.UserResponse;
import com.dollop.app.service.IChatRoomService;
import com.dollop.app.service.IMessageService;
import com.dollop.app.service.IMessageStatusService;
import com.dollop.app.service.IUserService;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private IUserService userService;
	@Autowired
	private IChatRoomService roomService;
	@Autowired
	private IMessageRepository messageRepository;
	@Autowired
	@Lazy
	private IChatRoomService chatRoomService;
	@Autowired
	private OnlineUserTracker onlineUserTracker;
	@Autowired
	private IMessageVisibilityRepository messageVisibilityRepository;
    @Autowired
    private IMessageStatusService messageStatusService;
	@Autowired
	@Lazy
	private SocketModule module;
	@Override
	public RoomMessageResponse sendMessage(String email, RoomMessageRequest request) {
		System.err.println(" reply msg id .........." +request.getReplyMessageId());
		User sender = userService.findByEmail(email);

		ChatRoom chatRoom = roomService.findRoomById(request.getChatRoomId());

		Message message = Message.builder().chatRoom(chatRoom).sender(sender).content(request.getContent())
				.messageType(MessageType.TEXT).build();

		if(request.getReplyMessageId()!=null)
		{
			Message replyMessage = messageRepository.findById(request.getReplyMessageId()).orElseThrow(()-> new ResourceNotFound(MessageConstants.NOT_FOUND_MESSAGE));
			message.setReplyMessage(replyMessage);
		}
		// Save to DB first
		List<MessageStatus> messageStatusList = new ArrayList<>();



		Message savedMessage = messageRepository.save(message);
		for (RoomMembers member : chatRoom.getMembers()) {
			if((!member.getUser().getEmail().equals(sender.getEmail()))&&member.getIsAvailableInRoom()) {
				System.err.println(" receivers emails ......................... " +member.getUser().getEmail());
				MessageStatus status = MessageStatus.builder().receiver(member.getUser()).message(savedMessage).build();
				messageStatusService.saveMessageStatus(status);
				messageStatusList.add(status);
			}
		}
  
		message.setStatusType(messageStatusList);
		Message newMessage = messageRepository.save(message);
		UserResponse userResponse = userService.userToUserResponse(sender);

		if (chatRoom.isGroup()) {
			List<String> emails = chatRoom.getMembers().stream().filter(m -> !m.getIsAvailableInRoom())
					.map(m -> m.getUser().getEmail()).toList();
			System.err.println("emails of removed members ==== ++++ = > " + emails);
			emails.forEach(e -> deleteMessageForRemovedMembers(savedMessage.getId(), e));
		}
		// Construct response payload to emit
		RoomMessageResponse replyMessageResponse =null;
		if(savedMessage.getReplyMessage()!=null)
		{
				replyMessageResponse = RoomMessageResponse.builder().id(newMessage.getReplyMessage().getId()).sender(userResponse)
						.content(newMessage.getReplyMessage().getContent()).messageType(newMessage.getReplyMessage().getMessageType()).statusType(messageStatusToMessageStatusResponse(newMessage.getReplyMessage().getStatusType()))
						.createdAt(Timestamp.valueOf(LocalDateTime.now())).build();

		}
		RoomMessageResponse response = RoomMessageResponse.builder().id(newMessage.getId()).sender(userResponse)
				.content(newMessage.getContent()).messageType(newMessage.getMessageType()).statusType(messageStatusToMessageStatusResponse(newMessage.getStatusType()))
				.createdAt(Timestamp.valueOf(LocalDateTime.now())).replyMessageResponse(replyMessageResponse	).build();

		return response;
	}

	private void deleteMessageForRemovedMembers(String id, String email) {
		User user = userService.findByEmail(email);
		Message messageById = findMessageById(id);
		MessageVisibility messageVisibility = MessageVisibility.builder().user(user).message(messageById).visible(false)
				.build();
		messageVisibilityRepository.save(messageVisibility);
	}

	@Override
	public ApiResponse getMessagesByRoomId(String chatRoomId) {
//		List<Message> messagesList = messageRepository.findByChatRoom_IdAndIsDeleteFalseOrderByCreatedAtAsc(chatRoomId);
		User currentUserById = userService.findCurrentUserById();
		List<Message> messagesList = messageRepository.findVisibleMessagesForUser(chatRoomId, currentUserById.getId());
		if (messagesList.isEmpty())
			return ApiResponse.builder().message("room have no messages").response(null).build();

		return ApiResponse.builder().message("message by room id")
				.response(Map.of("messages", messageToMessageResponse(messagesList))).build();
	}

	@Override
	public ApiResponse deleteMessages(List<String> messageIds) {
		messageIds.stream().forEach(id -> {
			Message message = findMessageById(id);
			message.setIsDelete(true);
			messageRepository.save(message);
		});
		return ApiResponse.builder().message("Message Updated Successfully").build();

	}

	@Override
	public Message findMessageById(String messageId) {
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new ResourceNotFound(MessageConstants.MESSAGE_NOT_FOUND));
		return message;
	}

	public MessageStatusResponse messageStatusToMessageStatusResponse(MessageStatus messageStatus)
	{
		return MessageStatusResponse.builder()
				.id(messageStatus.getId())
				.userResponse(userService.userToUserResponse(messageStatus.getReceiver()))
				.statusType(messageStatus.getStatusType())
				.build();
	}
	
	public List<MessageStatusResponse> messageStatusToMessageStatusResponse(List<MessageStatus> messageStatus)
	{
		return messageStatus.stream().map(this::messageStatusToMessageStatusResponse).toList();
	}
	
	public List<RoomMessageResponse> messageToMessageResponse(List<Message> messages) {
		return messages.stream().map(this::messageToMessageResponse).toList();
	}

	@Transactional
	public RoomMessageResponse messageToMessageResponse(Message message) {
		RoomMessageResponse replyMessageResponse =null;
		if(message.getReplyMessage()!=null)
		{
			replyMessageResponse = RoomMessageResponse.builder().id(message.getReplyMessage().getId())
	        .chatRoom(chatRoomService.chatRoomToChatRoomResponse(message.getReplyMessage().getChatRoom()))
			.sender(userService.userToUserResponse(message.getReplyMessage().getSender())).messageType(message.getReplyMessage().getMessageType())
			.content(message.getReplyMessage().getContent()).statusType(messageStatusToMessageStatusResponse(message.getReplyMessage().getStatusType()))
			.createdAt(Timestamp.valueOf(message.getCreatedAt())).build();
		}
		
		return RoomMessageResponse.builder().id(message.getId())
		        .chatRoom(chatRoomService.chatRoomToChatRoomResponse(message.getChatRoom()))
				.sender(userService.userToUserResponse(message.getSender())).messageType(message.getMessageType())
				.content(message.getContent()).statusType(messageStatusToMessageStatusResponse(message.getStatusType()))
				.createdAt(Timestamp.valueOf(message.getCreatedAt())).replyMessageResponse(replyMessageResponse).build();
	}
	
	public List<RoomMessageResponse> messageToMessageResponseForDeleteOnly(List<Message> messages) {
		return messages.stream().map(this::messageToMessageResponseForDeleteOnly).toList();
	}

	@Transactional
	public RoomMessageResponse messageToMessageResponseForDeleteOnly(Message message) {
		return RoomMessageResponse.builder().id(message.getId())
//		        .chatRoom(chatRoomService.chatRoomToChatRoomResponse(message.getChatRoom()))
				.sender(userService.userToUserResponse(message.getSender())).messageType(message.getMessageType())
				.content(message.getContent()).statusType(messageStatusToMessageStatusResponse(message.getStatusType()))
				.createdAt(Timestamp.valueOf(message.getCreatedAt())).build();
	}

//	@Override
//	public void updateMessageStatus(MessageStatusUpdateRequest messageStatusUpdateRequest, SocketIOServer server) {
//
//		System.err.println("== > update status called == > " + messageStatusUpdateRequest.getMessageId());
//		Optional<Message> optionalMessage = messageRepository.findById(messageStatusUpdateRequest.getMessageId());
//		
//		if (optionalMessage.isPresent()) {
//			Message message = optionalMessage.get();
//			List<MessageStatus> statusType = message.getStatusType();
//			com.dollop.app.enums.MessageStatus newStatus = com.dollop.app.enums.MessageStatus.valueOf(messageStatusUpdateRequest.getStatus());
//
//			// Update status only if it's progressing forward
//			if (message.getStatusType().ordinal() < newStatus.ordinal()) {
//
//				message.setStatusType(newStatus);
//				messageRepository.save(message);
//
//				for (SocketIOClient connectedClient : server.getAllClients()) {
//					String email = connectedClient.get("email");
//					if (email != null && email.equals(messageStatusUpdateRequest.getSenderEmail())) {
//						System.err.println("-------------> Msg emitting to the sender ===== > " + email);
//						connectedClient.sendEvent("status_update", messageStatusUpdateRequest);
//					}
//				}
//
//			}
//		}
//	}

	@Override
	public MessageStatusResponse updateMessageStatus(MessageStatusUpdateRequest messageStatusUpdateRequest, SocketIOServer server) {
    Optional<MessageStatus> byMessageIdAndReceiverId = messageStatusService.findByMessageIdAndReceiverId(messageStatusUpdateRequest.getMessageId(),messageStatusUpdateRequest.getReceiverEmail());
	if (onlineUserTracker.getOnlineUsers().contains(messageStatusUpdateRequest.getReceiverEmail())) {
		System.err.println(byMessageIdAndReceiverId.isPresent() + " == > update status called == > " + messageStatusUpdateRequest.getMessageId() +"rec email .... " +messageStatusUpdateRequest.getReceiverEmail());
		if (byMessageIdAndReceiverId.isPresent()) {
			System.err.println("1..........");
			 MessageStatus messageStatus = byMessageIdAndReceiverId.get();
			com.dollop.app.enums.MessageStatus newStatus = com.dollop.app.enums.MessageStatus.valueOf(messageStatusUpdateRequest.getStatus());

			// Update status only if it's progressing forward
			if (messageStatus.getStatusType().ordinal() < newStatus.ordinal()) {
				System.err.println("2..........");
				
				messageStatus.setStatusType(newStatus);
				MessageStatus updatedMessageStatus =messageStatusService.saveMessageStatus(messageStatus);
				System.err.println("3.........." +updatedMessageStatus.getReceiver().getEmail());
				
				return messageStatusToMessageStatusResponse(updatedMessageStatus);

			}
			
		}
		return null;
	}else
	{
		System.err.println("4..........");
		
		return messageStatusToMessageStatusResponse(byMessageIdAndReceiverId.get());
	}
	}
	@Override
	public void markStatusDeliveredByReceiverEmail(String receiverEmail, SocketIOServer server) {
		// 🔍 1. Fetch all SENT messages where receiverEmail == current user

		System.err.println("mark status delivered called............ 12233" + receiverEmail);

		List<MessageStatus> messagesStatusToDeliver = messageStatusService.findAllUnseenMessagesForReceiver(receiverEmail);

		messageRepository.markAllMessagesAsDeliveredForReceiver(receiverEmail);
		
		
		// 📤 3. Notify sender(s) about delivery (optional)
		if (onlineUserTracker.getOnlineUsers().contains(receiverEmail)) {
			System.err.println("s1.........." +messagesStatusToDeliver.size());
			for (MessageStatus msgStatus : messagesStatusToDeliver) {
				System.err.println("s2..........");
				for (SocketIOClient connectedClient : server.getAllClients()) {
					System.err.println("s3..........");
					String email = connectedClient.get("email");
					System.err.println("sender email === > " + email);
					if (email != null && email.equals(msgStatus.getMessage().getSender().getEmail())) {
						System.err.println("s4..........");
						msgStatus.setStatusType(com.dollop.app.enums.MessageStatus.DELIVERED);
						System.err.println("s5.........." +msgStatus.getStatusType());
						connectedClient.sendEvent("status_update" ,Map.of("updateMessageStatus",messageStatusToMessageStatusResponse(msgStatus),"messageId" ,msgStatus.getMessage().getId()));
					}
				}
			}
		}
	}

	@Override
	public ApiResponse deleteMessageForMe(List<String> messageIds) {
		User currentUser = userService.findCurrentUserById();
		List<MessageVisibility> visibilitiesToSave = new ArrayList<>();
		for (String messageId : messageIds) {
			Message message = messageRepository.findById(messageId)
					.orElseThrow(() -> new ResourceNotFound(MessageConstants.NOT_FOUND_MESSAGE));

			Optional<MessageVisibility> existingMessageAndUser = messageVisibilityRepository
					.findByMessageAndUser(message, currentUser);

			if (existingMessageAndUser.isPresent()) {
				existingMessageAndUser.get().setVisible(false);
			} else {

				visibilitiesToSave
						.add(MessageVisibility.builder().user(currentUser).message(message).visible(false).build());

			}
		}
		if (!visibilitiesToSave.isEmpty()) {
			messageVisibilityRepository.saveAll(visibilitiesToSave);
		}
		module.deleteForMe(currentUser.getEmail());
		return ApiResponse.builder().message("message deleted successully !").build();
	}

	@Override
	public List<RoomMessageResponse> deleteMessageForEveryOne(List<String> messageIds) {
		System.err.println("delete for every one called ...... " + messageIds);
		int i = 0;
//		User currentUser = userService.findCurrentUserById();
		List<Message> messages = new ArrayList<>();
		for (String messageId : messageIds) {
			System.err.println("delete for every one called ...... " + i++);

			Message message = messageRepository.findById(messageId)
					.orElseThrow(() -> new ResourceNotFound(MessageConstants.NOT_FOUND_MESSAGE));

			System.err.println(" ===== > 1" + messageId);
			int deleteMessage = messageRepository.deleteMessage(messageId);
			System.err.println(" ===== > 2");
			System.err.println("msg deleted ... == > " + deleteMessage);
			messages.add(message);
			System.err.println(" ===== > 3");

		}
		System.err.println(" ===== > 4");
		return messageToMessageResponseForDeleteOnly(messages);
	}

	@Override
	public LastMessageResponse getLastMessageOfRoom(String roomId) {
		System.err.println("1................" + roomId);
		User currentUser = userService.findCurrentUserById();
		RoomMembers member = chatRoomService.findRoomById(roomId).getMembers().stream()
				.filter(m -> m.getUser().getEmail().equals(currentUser.getEmail())).findFirst().orElse(null);

		if (member.getIsAvailableInRoom()) {
			// agr updated at me dikkt aye to joined at field bna lenge or usme jab add hoga
			// wo date store krwa denge for getting msg of after added in group
			Message lastMessage = messageRepository
				    .findLastVisibleMessage(roomId, member.getUpdatedAt(), currentUser.getId(), PageRequest.of(0, 1))
				    .stream().findFirst().orElse(null);;
			if (lastMessage != null) {
				return LastMessageResponse.builder().content(lastMessage.getContent())
						.sender(userService.userToUserResponse(lastMessage.getSender()))
						.createdAt(Timestamp.valueOf(lastMessage.getCreatedAt())).build();
			}
		}
		return null;
	}

	@Override
	public Integer getUnSeenMessageCountByRoomId(String id) {
		User currentUser = userService.findCurrentUserById();
		
		return messageRepository.countUnseenMessages(id, currentUser.getEmail());
		
		
	}
}
