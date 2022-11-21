package com.fmi.homeflow.exception.user_exception;

import com.fmi.homeflow.exception.ResourceAlreadyExistsException;

import java.util.UUID;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public <T> UserAlreadyExistsException(T criteria) {
        super("User by criteria " + criteria.toString() + " already exists!");
    }
}
