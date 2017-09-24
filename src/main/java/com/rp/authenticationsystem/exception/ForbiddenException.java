package com.rp.authenticationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3423648605578630758L;

	public ForbiddenException() {
		super();
	}
	
	public ForbiddenException(String message) {
		super(message);
	}
}
