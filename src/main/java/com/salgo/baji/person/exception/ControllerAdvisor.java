package com.salgo.baji.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(PersonException.class)
  public ResponseEntity<Object> handleUserException(PersonException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }
}
