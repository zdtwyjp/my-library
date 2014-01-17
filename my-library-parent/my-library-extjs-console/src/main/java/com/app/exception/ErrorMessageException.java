package com.app.exception;

@SuppressWarnings("serial")
public class ErrorMessageException extends RuntimeException {
    public ErrorMessageException() {
	super();
    }

    public ErrorMessageException(String message, Throwable cause) {
	super(message, cause);
	if (cause != null)
	    cause.printStackTrace();
    }

    public ErrorMessageException(String message) {
	super(message);
    }

    public ErrorMessageException(Throwable cause) {
	super(cause);
    }
}