package com.xantrix.webapp.exception;

public class ConflictException extends Exception {
	private static final long serialVersionUID = 1L;

	private String message;

	public ConflictException() {
		this("Elemento Già esistente in anagrafica");
	}
	
	public ConflictException(String message) {
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
