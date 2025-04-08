package com.mysociety.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@RestController
@ControllerAdvice
public class MysocietyExceptionHandler {
	
	@ExceptionHandler(Mysocietyexception.class)
	public ResponseEntity<?>handlerResourceNotFoundException(Mysocietyexception exception,
			WebRequest request)
	{
	ErrorDetails error = new ErrorDetails(HttpStatus.CONFLICT.value(),new Date(),exception.getMessage());
	return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
}
