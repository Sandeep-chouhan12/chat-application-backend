package com.dollop.app.exceptions;

public class InvalideEmailFormateException extends RuntimeException{

	public InvalideEmailFormateException(String message) {
		super(message);
		
	}

	public InvalideEmailFormateException() {
		super();
	}
	

}
