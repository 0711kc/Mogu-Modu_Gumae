package com.bunsaned3thinking.mogu.common.exception.handler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.persistence.EntityNotFoundException;

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

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<String> handleSqlIntegrityConstraintViolationException(
		SQLIntegrityConstraintViolationException exception) {
		int errorCode = exception.getErrorCode();
		String message;
		if (errorCode == 1062) { // MySQL의 중복 키 에러 코드
			message = "[Error] 중복 키로 인한 예외가 발생했습니다.";
		} else if (errorCode == 1452) { // MySQL의 외래 키 위반 에러 코드
			message = "[Error] 외래 키 제약 조건 위반이 발생했습니다.";
		} else {
			message = "[Error] 기타 무결성 제약 조건 위반이 발생했습니다.";
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnrecognizedPropertyException.class)
	public ResponseEntity<String> handleUnrecognizedPropertyException(UnrecognizedPropertyException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body("[Error] 잘못된 Key 값이 들어왔습니다. : " + exception.getPropertyName());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(
		EntityNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}
}
