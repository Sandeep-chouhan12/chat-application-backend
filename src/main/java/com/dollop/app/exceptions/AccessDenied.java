package com.dollop.app.exceptions;

public class AccessDenied extends RuntimeException{

	public AccessDenied() {
		super();
	}

	public AccessDenied(String message) {
		super(message);
	}

}
