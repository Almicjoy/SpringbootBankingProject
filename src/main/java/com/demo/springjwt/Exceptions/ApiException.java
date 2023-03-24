package com.demo.springjwt.Exceptions;

import org.springframework.http.HttpStatus;

public class ApiException {
	
	private final String message;
	private final HttpStatus httpStatus;
	private final Throwable throwable;
	public String getMessage() {
		return message;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public ApiException(String message, HttpStatus httpStatus, Throwable throwable) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.throwable = throwable;
	}
	
	

}
