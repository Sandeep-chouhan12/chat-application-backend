package com.dollop.app.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LastMessageResponse {

	 private String content;
	 private UserResponse sender;
	 private Timestamp createdAt;
}
