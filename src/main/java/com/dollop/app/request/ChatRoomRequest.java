package com.dollop.app.request;

import java.util.List;

import com.dollop.app.constant.MessageConstants;
import com.dollop.app.constant.ValidationConstants;
import com.dollop.app.entity.RoomMembers;
import com.dollop.app.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomRequest {
	@JsonProperty("isGroup")
	private boolean isGroup;

    private String roomName; // For group chat

    private List<@NotBlank(message =MessageConstants.Id_NOT_NULL) @Pattern(regexp = ValidationConstants.ID_REGEX, message = MessageConstants.INVALID_UUID) String> memberIds;
    
  
}
