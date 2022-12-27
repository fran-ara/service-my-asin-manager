package com.myasinmanager.exception;

public class SPAPIException extends RuntimeException {

    private static final long serialVersionUID = -2823031305956722735L;

    public SPAPIException(String message) {
        super(message);
    }
    public SPAPIException(String message, Throwable cause) {
        super(message,cause);
    }
}
