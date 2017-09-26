package com.rp.authenticationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6891114363300772600L;

	public NotAuthorizedException() {
		super();
	}
	
	public NotAuthorizedException(String message) {
		super(message);
	}
}
