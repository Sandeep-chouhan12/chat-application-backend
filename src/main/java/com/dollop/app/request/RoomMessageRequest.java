package com.dollop.app.request;

import com.dollop.app.entity.ChatRoom;
import com.dollop.app.entity.User;
import com.dollop.app.enums.MessageStatus;
import com.dollop.app.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
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
public class RoomMessageRequest {

	@NotBlank(message = "The given room id must not be null") 
	@Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid UUID format") 
    @JsonProperty("chatRoomId")
	private String chatRoomId;

	@JsonProperty("content")
	@NotBlank(message = "The given content must not be null") 
    private String content;

	private String replyMessageId;
//    private String mediaUrl; // If it's an image/video/file

}
