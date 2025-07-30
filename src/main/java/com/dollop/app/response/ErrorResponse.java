package com.dollop.app.response;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
   
    private Integer status;
	private String message;
	@Builder.Default
	private LocalDateTime date = LocalDateTime.now();
	private String details;
	
}
