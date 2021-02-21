package com.xantrix.webapp.exception;

import java.io.Serializable;

public class ValidationException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	public ValidationException() {
		this("Errore di validazione!");
	}
	
	public ValidationException(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
