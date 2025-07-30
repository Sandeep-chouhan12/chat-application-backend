package com.dollop.app.service;

import java.util.List;

import com.dollop.app.entity.ChatRoom;
import com.dollop.app.request.AddMemberRequest;
import com.dollop.app.request.ChatRoomRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.response.ChatRoomResponse;

public interface IChatRoomService {
  
	   ApiResponse createRoom(ChatRoomRequest roomRequest);
	   
	   ApiResponse getRoomById(String id);
	   
	   ChatRoom findRoomById(String id);
	   
	   ApiResponse getAllRooms();
	   
	   ApiResponse addRoomMember(AddMemberRequest addMemberRequest);
	   
	   ApiResponse removeRoomMember(String id , List<String> memberIds);
	   
	   ApiResponse updateRoomName(String id , String newName );
	   
	   ApiResponse getRoomsForUser(String userId);
	   
	   ChatRoomResponse chatRoomToChatRoomResponse(ChatRoom chatRoom);
	   
	   List<ChatRoomResponse> chatRoomToChatRoomResponse(List<ChatRoom> chatRooms);
		

}
