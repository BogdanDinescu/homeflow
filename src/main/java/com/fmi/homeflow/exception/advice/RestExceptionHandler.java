package com.fmi.homeflow.exception.advice;

import com.fmi.homeflow.exception.ResourceNotFoundException;
import com.fmi.homeflow.exception.user_exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    protected ResponseEntity<Object> handleException(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

}
