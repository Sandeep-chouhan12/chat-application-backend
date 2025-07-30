package com.dollop.app.exceptions;

public class OtpExpiredException extends RuntimeException {

	public OtpExpiredException() {
		super();
		
	}

	public OtpExpiredException(String message) {
		super(message);
		
	}

}
