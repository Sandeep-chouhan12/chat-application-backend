package com.dollop.app.config;
import com.dollop.app.helper.TrimStringDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class JacksonConfig {
	  @Bean
	    public ObjectMapper objectMapper() {
	        
		  System.out.println("=====  JacksonConfig called  ===== ");
		  ObjectMapper objectMapper = new ObjectMapper();
	        SimpleModule module = new SimpleModule();
	        module.addDeserializer(String.class, new TrimStringDeserializer());
	        objectMapper.registerModule(module);
	        objectMapper.registerModule(new JavaTimeModule());
	        return objectMapper;
	    }
}
