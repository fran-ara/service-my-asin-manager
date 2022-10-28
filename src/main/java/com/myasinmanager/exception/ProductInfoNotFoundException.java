package com.myasinmanager.exception;

public class ProductInfoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2823031305956722735L;
	
	public ProductInfoNotFoundException(String message) {
		super(message);
	}
	
}
