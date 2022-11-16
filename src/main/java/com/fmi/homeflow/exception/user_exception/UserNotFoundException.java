package com.fmi.homeflow.exception.user_exception;

import com.fmi.homeflow.exception.ResourceNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(UUID id) {
        super("User with id + " + id + " not found.");
    }

}
