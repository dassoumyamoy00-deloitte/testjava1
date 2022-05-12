package com.usermanagement.usermanagement.exceptions;

public class UserAlreadyPresentInDatabaseException extends Exception {

    public UserAlreadyPresentInDatabaseException(String message) {
        super(message);
    }
}

