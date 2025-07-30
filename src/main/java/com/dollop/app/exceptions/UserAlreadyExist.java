package com.dollop.app.exceptions;

public class UserAlreadyExist extends RuntimeException{

	public UserAlreadyExist() {
		super();
		
	}

	public UserAlreadyExist(String message) {
		super(message);
	}

}
