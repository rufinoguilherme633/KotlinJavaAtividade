package com.rufinoguilherme.listaCompras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> businessException(BusinessException ex){
		ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
		
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException ex){
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	
	

}
