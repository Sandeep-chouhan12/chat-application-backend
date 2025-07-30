package com.dollop.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ServerCommandLineRunner implements CommandLineRunner {
    @Autowired
	private  SocketIOServer server;
    @Override
    public void run(String... args) throws Exception {
    	 try {
             server.start();
             log.info("✅ Socket.IO server started successfully on port {}", server.getConfiguration().getPort());
         } catch (Exception e) {
             log.error("❌ Failed to start Socket.IO server", e);
         }
    }
    
    @PreDestroy
    public void shutDown()
    {
    	 try {
             server.start();
             log.info("✅ Socket.IO server stoped successfully on port {}", server.getConfiguration().getPort());
         } catch (Exception e) {
             log.error("❌ Failed to stop Socket.IO server", e);
         }
    }
}
