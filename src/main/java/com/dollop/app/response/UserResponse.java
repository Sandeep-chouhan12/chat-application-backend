package com.dollop.app.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.dollop.app.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {

	private String id;
		
    private String name;
 
    private String userName;
	
    private String email;

    private String profileImageUrl;

    private UserStatus status;

    private Timestamp lastSeen;
}
