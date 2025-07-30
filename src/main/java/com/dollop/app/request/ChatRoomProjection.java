package com.dollop.app.request;

public interface ChatRoomProjection {
	String getRoomId();
    String getRoomName();
    String getIsGroup();
    String getCreatedBy();
    String getLastMessageContent();
    String getLastMessageTime();
    String getLastMessageSenderId();
}
