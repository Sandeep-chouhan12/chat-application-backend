package com.dollop.app.exceptions;

public class ExpiredTokenException extends RuntimeException
{

	public ExpiredTokenException() {
		super();
		
	}

	public ExpiredTokenException(String message) {
		super(message);
		
	}

}
