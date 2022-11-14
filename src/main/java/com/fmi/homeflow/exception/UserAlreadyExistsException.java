package com.fmi.homeflow.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String id) {
        super("User with id + " + id + "not found");
    }
}
