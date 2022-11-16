package com.fmi.homeflow.exception.user_exception;

import java.util.UUID;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(UUID id) {
        super("User with id " + id + " already exists");
    }
}
