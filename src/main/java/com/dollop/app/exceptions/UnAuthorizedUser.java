package com.dollop.app.exceptions;

public class UnAuthorizedUser extends RuntimeException{

	public UnAuthorizedUser() {
		super();
		
	}

	public UnAuthorizedUser(String message) {
		super(message);
	
	}

}
