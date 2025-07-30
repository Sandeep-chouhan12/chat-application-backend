package com.dollop.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.entity.RoomMembers;

public interface IRoomMembersRepository extends JpaRepository<RoomMembers,String> {
	Boolean existsByChatRoom_IdAndUser_Id(String chatRoomId, String userId);

	Optional<RoomMembers> findByChatRoom_IdAndUser_Id(String roomId, String userId);
}
