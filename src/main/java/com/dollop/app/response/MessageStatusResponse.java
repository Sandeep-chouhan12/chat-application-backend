package com.dollop.app.response;

import com.dollop.app.enums.MessageStatus;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageStatusResponse {

	private String id;
	
	private UserResponse userResponse;
	
	private MessageStatus statusType;
}
