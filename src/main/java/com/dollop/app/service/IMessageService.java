package com.dollop.app.service;


import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.dollop.app.entity.Message;
import com.dollop.app.enums.MessageStatus;
import com.dollop.app.request.MessageStatusUpdateRequest;
import com.dollop.app.request.RoomMessageRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.response.LastMessageResponse;
import com.dollop.app.response.MessageStatusResponse;
import com.dollop.app.response.RoomMessageResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public interface IMessageService {
	RoomMessageResponse sendMessage(String email,RoomMessageRequest request);

	ApiResponse getMessagesByRoomId(String chatRoomId);
	
	MessageStatusResponse updateMessageStatus(MessageStatusUpdateRequest messageStatusUpdateRequest, SocketIOServer server);
	
	ApiResponse deleteMessages(List<String> messageIds);
	
	public Message findMessageById(String messageId);

	void markStatusDeliveredByReceiverEmail(String receiverEmail,SocketIOServer server);
	
	ApiResponse deleteMessageForMe(List<String> messageIds);
	
	public List<RoomMessageResponse> deleteMessageForEveryOne(List<String> messageIds);

	LastMessageResponse getLastMessageOfRoom(String roomId);

	Integer getUnSeenMessageCountByRoomId(String id);

}
