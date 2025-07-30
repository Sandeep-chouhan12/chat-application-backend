package com.dollop.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.app.entity.RoomMembers;
import com.dollop.app.exceptions.ResourceNotFound;
import com.dollop.app.repository.IRoomMembersRepository;
import com.dollop.app.response.RoomMembersResponse;
import com.dollop.app.service.IRoomMembersService;
import com.dollop.app.service.IUserService;

@Service
public class RoomMembersServiceImpl implements IRoomMembersService {

	@Autowired
	private IRoomMembersRepository roomMembersRepository;
	@Autowired
	private IUserService userService;
	@Override
	public RoomMembersResponse roomMemberToRoomMemberResponse(RoomMembers member) {
		
		return RoomMembersResponse.builder()
				.id(member.getId())
				.user(userService.userToUserResponse(member.getUser()))
				.isAdmin(member.getIsAdmin())
				.isAvailableInRoom(member.getIsAvailableInRoom())
				.build();
		        
	}

	@Override
	public List<RoomMembersResponse> roomMemberToRoomMemberResponse(List<RoomMembers> members) {
		
		return members.stream().map(this::roomMemberToRoomMemberResponse).toList();
	}

	@Override
	public Boolean isUserMemberOfChatRoom(String roomId, String userId) {
		
		return roomMembersRepository.existsByChatRoom_IdAndUser_Id(roomId, userId);
	}
	
	@Override
	public RoomMembers getMemberOfChatRoomByUserId(String roomId, String userId) {
		
		return roomMembersRepository.findByChatRoom_IdAndUser_Id(roomId, userId).get();
	}

	@Override
	public RoomMembers findById(String roomMemberId) {
		RoomMembers roomMember=roomMembersRepository.findById(roomMemberId).orElseThrow(()-> new ResourceNotFound("Member Not Found !"));
		return roomMember;
	}

	@Override
	public void updateIsMemberAvailability(RoomMembers roomMember) {
		roomMembersRepository.save(roomMember);
		
	}

}
