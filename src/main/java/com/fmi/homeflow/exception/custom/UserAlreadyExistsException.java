package com.fmi.homeflow.exception.custom;

import com.fmi.homeflow.exception.ResourceAlreadyExistsException;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public UserAlreadyExistsException(String name) {
        super("User with username \"" + name + "\" already exists!");
    }
}
