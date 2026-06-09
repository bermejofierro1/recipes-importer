package com.miguelbermejo.roomrecipes.exception;

public class TheMealDbClientException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3324835653115009880L;

	public TheMealDbClientException(String message) {
		super(message);
	}

	public TheMealDbClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
