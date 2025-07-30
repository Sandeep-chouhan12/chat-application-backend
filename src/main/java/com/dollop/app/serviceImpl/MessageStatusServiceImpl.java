package com.dollop.app.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.app.entity.MessageStatus;
import com.dollop.app.repository.IMessageStatusRepository;
import com.dollop.app.service.IMessageStatusService;

@Service
public class MessageStatusServiceImpl implements IMessageStatusService {

	@Autowired
	private IMessageStatusRepository messageStatusRepository;
	@Override
	public MessageStatus saveMessageStatus(MessageStatus status) {
		
		return messageStatusRepository.save(status);
	}
	@Override
	public Optional<MessageStatus> findByMessageIdAndReceiverId(String messageId, String receiverEmail) {
		
		return messageStatusRepository.findByMessageIdAndReceiverId(messageId, receiverEmail);
	}
	@Override
	public List<MessageStatus> findAllUnseenMessagesForReceiver(String receiverEmail) {
		
		return messageStatusRepository.findAllUnseenMessagesForReceiver(receiverEmail);
	}

}
