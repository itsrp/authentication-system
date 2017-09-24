package com.rp.authenticationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3518065494850538370L;

	public BadRequestException() {
		super();
	}
	
	public BadRequestException(String message) {
		super(message);
	}
}
