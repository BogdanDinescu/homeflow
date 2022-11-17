package com.fmi.homeflow.exception.user_exception;

import com.fmi.homeflow.exception.ResourceAlreadyExistsException;

import java.util.UUID;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public UserAlreadyExistsException(UUID id) {
        super("User with id " + id + " already exists");
    }
}
