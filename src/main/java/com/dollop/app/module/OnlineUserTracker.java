package com.dollop.app.module;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class OnlineUserTracker {
    private Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public void addUser(String email) {
    	System.err.println("connecting "+email);
        onlineUsers.add(email);
    }

    public void removeUser(String email) {
        onlineUsers.remove(email);
    }

    public boolean isOnline(String email) {
        return onlineUsers.contains(email);
    }

    public Set<String> getOnlineUsers() {
        return onlineUsers;
    }
}
