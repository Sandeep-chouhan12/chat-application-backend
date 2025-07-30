package com.dollop.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AppUtils {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private HttpServletRequest servletRequest;

	public String getTokenFromHeader() {
		String token = this.servletRequest.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer "))
			token = token.substring(7);
		
		return token;
	}
	public String getIdFromToken()
	{
		String token = this.getTokenFromHeader();
		String userId = (String)jwtUtils.getHeader(token, "userId");
		return userId;
	}
}
