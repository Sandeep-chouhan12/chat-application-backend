package com.dollop.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.app.entity.MessageStatus;


public interface IMessageStatusRepository extends JpaRepository<MessageStatus,String> {

	 @Query("SELECT ms FROM MessageStatus ms WHERE ms.message.id = :messageId AND ms.receiver.email = :receiverEmail")
	 Optional<MessageStatus> findByMessageIdAndReceiverId(@Param("messageId") String messageId, @Param("receiverEmail") String receiverEmail);

	 
	 @Query("""
			    SELECT ms FROM MessageStatus ms
			    WHERE ms.statusType = com.dollop.app.enums.MessageStatus.SENT
			      AND ms.receiver.email = :receiverEmail
			      AND ms.message.sender.email <> :receiverEmail
			""")
	List<MessageStatus> findAllUnseenMessagesForReceiver(@Param("receiverEmail") String receiverEmail);
		
}
