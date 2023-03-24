package com.demo.springjwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
		ApiException apiException = new ApiException(
				e.getMessage(),
				HttpStatus.BAD_REQUEST,
				e
				);
		return ResponseEntity.badRequest().body(apiException);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
		ApiException apiException = new ApiException(
				e.getMessage(),
				HttpStatus.NOT_FOUND,
				e
				);
		return ResponseEntity.badRequest().body(apiException);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
		ApiException apiException = new ApiException(
				e.getMessage(),
				HttpStatus.FORBIDDEN,
				e
				);
		return ResponseEntity.badRequest().body(apiException);
	}

}
