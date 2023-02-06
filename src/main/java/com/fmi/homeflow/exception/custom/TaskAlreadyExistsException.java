package com.fmi.homeflow.exception.custom;

import com.fmi.homeflow.exception.ResourceAlreadyExistsException;

import java.util.UUID;

public class TaskAlreadyExistsException extends ResourceAlreadyExistsException {

    public TaskAlreadyExistsException(UUID id) {
        super("Task with id " + id + " already exists");
    }
}
