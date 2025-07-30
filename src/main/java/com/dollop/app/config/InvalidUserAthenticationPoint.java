package com.dollop.app.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.dollop.app.exceptions.ExpiredTokenException;
import com.dollop.app.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class InvalidUserAthenticationPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException 
	{
		
		Exception exception = (Exception) request.getAttribute("exception");
		
		 
		System.out.println("exception ==== >" +authException);
		
		System.err.println("SecurityFilter applied on: " + request.getRequestURI());

		System.err.println(authException);
		int status = HttpServletResponse.SC_UNAUTHORIZED;
	
		String message = "User Unauthorized";

		if (exception instanceof ExpiredJwtException) {
			message = "Token has expired. Please log in again.";
		} else if (exception instanceof SignatureException) {
			message = "Invalid token signature.";
		} else if (exception instanceof MalformedJwtException) {
			message = "Malformed JWT token.";
		} else if (exception instanceof UnsupportedJwtException) {
			message = "Unsupported JWT token.";
		} else if (exception instanceof ExpiredTokenException) {
			message = authException.getMessage();
		}else if (authException instanceof InsufficientAuthenticationException) {
			message = authException.getMessage();
		}

		// Convert map to JSON
		response.setContentType("application/json");
		response.setStatus(status);
		Map<Object, String> m = new HashMap<>();
		m.put("message", message);
		response.getWriter()
				.write(new ObjectMapper().writeValueAsString(m));
		response.getWriter().flush();
	}
		}

//response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authentication Failed!");
