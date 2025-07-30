package com.dollop.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;

import jakarta.annotation.PostConstruct;



@Configuration
public class SocketIOConfig {
	    @Value("${socket-server.host}")
	    private String host;

	    @Value("${socket-server.port}")
	    private Integer port;

	    @Bean
	    public SocketIOServer socketIOServer() {
	        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
	        config.setHostname(host);
	        config.setPort(port);
	        config.setOrigin("http://192.168.1.8:4200");
	        config.setTransports(Transport.WEBSOCKET,Transport.POLLING);
	        config.setPingTimeout(60000); // Increase timeout
	    	config.setPingInterval(25000);
	        return new SocketIOServer(config);
	    }
}
