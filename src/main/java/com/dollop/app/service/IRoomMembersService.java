package com.dollop.app.service;

import java.util.List;

import com.dollop.app.entity.RoomMembers;
import com.dollop.app.response.RoomMembersResponse;

public interface IRoomMembersService {

	public RoomMembersResponse roomMemberToRoomMemberResponse(RoomMembers member);
	
	public List<RoomMembersResponse> roomMemberToRoomMemberResponse(List<RoomMembers> members);
	
	public Boolean isUserMemberOfChatRoom(String roomId , String userId);

	public RoomMembers findById(String roomMemberId);

	public void updateIsMemberAvailability(RoomMembers roomMember);
	
	public RoomMembers getMemberOfChatRoomByUserId(String roomId, String userId);
}
