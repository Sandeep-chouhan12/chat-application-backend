package com.dollop.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChatApplication43Application {
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication43Application.class, args);
		System.out.println("---->Chat Application Project<----");
	}

}
