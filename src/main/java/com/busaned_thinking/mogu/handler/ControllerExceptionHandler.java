package com.busaned_thinking.mogu.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		List<String> messages = exception.getBindingResult().getAllErrors().stream()
			.map(ObjectError::getDefaultMessage)
			.toList();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
	}
}
