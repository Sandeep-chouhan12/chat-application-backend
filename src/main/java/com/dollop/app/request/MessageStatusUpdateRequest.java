package com.dollop.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageStatusUpdateRequest {
    private String messageId;
    private String status; // "DELIVERED" or "SEEN"
    private String senderEmail;
    private String receiverEmail;
    // getters + setters
}