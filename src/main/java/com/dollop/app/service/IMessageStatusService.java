package com.dollop.app.service;

import java.util.List;
import java.util.Optional;

import com.dollop.app.entity.MessageStatus;

public interface IMessageStatusService {

	MessageStatus saveMessageStatus(MessageStatus status);

	Optional<MessageStatus> findByMessageIdAndReceiverId(String messageId, String receiverEmail);

	List<MessageStatus> findAllUnseenMessagesForReceiver(String receiverEmail);
   
}
