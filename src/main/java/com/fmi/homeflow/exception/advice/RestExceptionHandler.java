package com.fmi.homeflow.exception.advice;

import com.fmi.homeflow.exception.InvalidDataException;
import com.fmi.homeflow.exception.ResourceAlreadyExistsException;
import com.fmi.homeflow.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.Optional.ofNullable;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleException(ResourceNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    protected ResponseEntity<Object> handleException(ResourceAlreadyExistsException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {InvalidDataException.class})
    protected ResponseEntity<Object> handleException(InvalidDataException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {
        FieldError fieldError = ofNullable(exception.getFieldError())
            .orElse(new FieldError("unknown", "unknown", "unknown")
            );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Invalid input\n" +
                fieldError.getField() +
                " " +
                fieldError.getDefaultMessage()
            );
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleException(UsernameNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Internal server error!\n" + exception.getMessage());
    }

}
