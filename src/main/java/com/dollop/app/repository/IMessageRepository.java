package com.dollop.app.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dollop.app.entity.ChatRoom;
import com.dollop.app.entity.Message;
import com.dollop.app.entity.MessageStatus;

import java.time.LocalDateTime;
import java.util.List;


public interface IMessageRepository extends JpaRepository<Message, String>{
	List<Message> findByChatRoom_IdAndIsDeleteFalseOrderByCreatedAtAsc(String chatRoomId);

	
	@Query("""
			  SELECT m FROM Message m
			  WHERE   m.isDelete = false AND m.chatRoom.id = :roomId AND (
			    NOT EXISTS ( 
			      SELECT mv FROM MessageVisibility mv
			      WHERE mv.message = m AND mv.user.id = :userId AND mv.visible = false
			    )
			  )
			 
			  ORDER BY m.createdAt ASC
			""")
	List<Message> findVisibleMessagesForUser(@Param("roomId") String roomId, @Param("userId") String userId);
	
	
	@Transactional
	@Modifying
	@Query("""
		    UPDATE MessageStatus ms
		    SET ms.statusType = com.dollop.app.enums.MessageStatus.DELIVERED
		    WHERE ms.receiver.email = :receiverEmail
		      AND ms.statusType = com.dollop.app.enums.MessageStatus.SENT
		      AND ms.message.sender.email <> :receiverEmail
		""")
	int markAllMessagesAsDeliveredForReceiver(@Param("receiverEmail") String receiverEmail);


	@Transactional
	@Modifying
	@Query("UPDATE Message m SET m.isDelete= true WHERE m.id = :messageId")
	int deleteMessage(@Param("messageId") String messageId);
	
	Message findTopByChatRoomIdOrderByCreatedAtDesc(String chatRoomId);


	Message findTopByChatRoomIdAndCreatedAtAfterAndIsDeleteFalseOrderByCreatedAtDesc(
		    String roomId,
		    LocalDateTime updatedAt
		);
	
	@Query("""
		    SELECT m FROM Message m
		    WHERE m.chatRoom.id = :roomId
		    AND m.createdAt > :joinedAt
		    AND m.isDelete = false
		    AND m.id NOT IN (
		        SELECT mv.message.id FROM MessageVisibility mv
		        WHERE mv.user.id = :userId
		    )
		    ORDER BY m.createdAt DESC
		""")
		List<Message> findLastVisibleMessage(
		    @Param("roomId") String roomId,
		    @Param("joinedAt") LocalDateTime joinedAt,
		    @Param("userId") String userId,
		    Pageable pageable
		);
	
	@Query("""
		    SELECT COUNT(ms)
		    FROM MessageStatus ms
		    WHERE ms.message.chatRoom.id = :roomId
		      AND ms.receiver.email = :currentEmail
		      AND ms.statusType = com.dollop.app.enums.MessageStatus.DELIVERED
		      AND ms.message.isDelete = false
		""")
    Integer countUnseenMessages(@Param("roomId") String roomId, @Param("currentEmail") String currentEmail);

}


