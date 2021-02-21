package com.xantrix.webapp.exception;

import java.io.Serializable;

public class NotFoundException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	public NotFoundException() {
		this("Elemento Ricercato Non Trovato!");
	}
	
	public NotFoundException(String message) {
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
