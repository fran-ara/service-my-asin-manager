package com.myasinmanager.exception;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = -2823031305956722735L;

	public ConflictException(String message) {
		super(message);
	}
	
}
