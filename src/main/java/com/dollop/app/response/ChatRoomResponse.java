package com.dollop.app.response;

import java.util.List;

import com.dollop.app.entity.RoomMembers;
import com.dollop.app.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomResponse {
	    private String id;

	    private Boolean isGroup;

	    private String roomName; // For group chat

	    private UserResponse createdBy;

	    private LastMessageResponse lastMessage;
	    
	    private Integer unSeenMessageCount;
	    
	    private List<RoomMembersResponse> members;
}
