package com.dollop.app.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.app.entity.ChatRoom;
import com.dollop.app.request.ChatRoomProjection;

public interface IChatRoomRepository extends JpaRepository<ChatRoom, String> {

	@Query("""
			SELECT cr
			FROM ChatRoom cr
			JOIN cr.members p1
			JOIN cr.members p2
			WHERE cr.isGroup = false
			AND p1.user.id = :userId1
			AND p2.user.id = :userId2
			""")
	Optional<ChatRoom> findOneToOneChatRoomBetween(@Param("userId1") String userId1, @Param("userId2") String userId2);

//    boolean existsByChatRoomIdAndUserId(String chatRoomId, String userId);

	List<ChatRoom> findByMembers_User_Id(String userId);

	@Query(value = """
				    SELECT r.*
				    FROM chat_rooms r
				    JOIN chat_room_participants rm ON rm.chat_room_id = r.id
				    LEFT JOIN (
				        SELECT m1.chat_room_id, MAX(m1.created_at) AS lastMessageTime
				        FROM message m1
				        WHERE m1.is_delete = false
				          AND m1.id NOT IN (
				              SELECT mv.message_id FROM message_visibility mv
				              WHERE mv.user_id = :userId
				          )
				        GROUP BY m1.chat_room_id
				    ) m ON m.chat_room_id = r.id
				    WHERE rm.user_id = :userId AND rm.is_available_in_room = true
				   ORDER BY
			CASE WHEN m.lastMessageTime IS NULL THEN 1 ELSE 0 END,
			m.lastMessageTime DESC;
				    """, nativeQuery = true)
	List<ChatRoom> findSortedRooms(@Param("userId") String userId);

}
