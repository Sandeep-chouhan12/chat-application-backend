package com.dollop.app.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.dollop.app.entity.ChatRoom;
import com.dollop.app.entity.User;
import com.dollop.app.enums.MessageStatus;
import com.dollop.app.enums.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomMessageResponse {

    private String id;
    
    private ChatRoomResponse chatRoom;

    private UserResponse sender;

    private MessageType messageType;
  
    private String content;

    private String mediaUrl; // If it's an image/video/file

    private List<MessageStatusResponse> statusType;
    
    private Timestamp createdAt;
    
    private RoomMessageResponse replyMessageResponse;
}
