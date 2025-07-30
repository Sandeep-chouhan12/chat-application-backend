package com.dollop.app.exceptions;

public class InvalidePasswordException extends RuntimeException{

	public InvalidePasswordException(String message) {
		super(message);
		
	}

	public InvalidePasswordException() {
		super();
	}

}
