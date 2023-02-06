package com.fmi.homeflow.exception.custom;

import com.fmi.homeflow.exception.ResourceNotFoundException;

import java.util.UUID;

public class TaskNotFoundException extends ResourceNotFoundException {
    public TaskNotFoundException(UUID id) {
        super("Task with id + " + id + " not found.");
    }
}
