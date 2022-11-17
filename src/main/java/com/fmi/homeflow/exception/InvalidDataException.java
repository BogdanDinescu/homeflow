package com.fmi.homeflow.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super("Invalid data. Check id's");
    }
}
