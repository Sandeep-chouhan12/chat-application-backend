package com.dollop.app.request;

import com.dollop.app.response.ChatRoomResponse;
import com.dollop.app.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypingStatusRequest {
 private ChatRoomResponse chatRoomResponses;
 private UserResponse currentUser;
}
