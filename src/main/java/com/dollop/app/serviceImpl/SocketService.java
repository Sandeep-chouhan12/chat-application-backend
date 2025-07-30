package com.dollop.app.serviceImpl;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.dollop.app.entity.Message;
import com.dollop.app.enums.ServerType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SocketService {

	 public void sendMessage(String room,String eventName, SocketIOClient senderClient, String message) {
	        for (
	                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
	            if (!client.getSessionId().equals(senderClient.getSessionId())) {
	                client.sendEvent(eventName,
	                        new Message(ServerType.SERVER, message));
	            }
	        }
	    }
}
