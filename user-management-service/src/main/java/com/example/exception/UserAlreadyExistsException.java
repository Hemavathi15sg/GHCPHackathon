package com.example.exception;

/**
 * Exception thrown when a user with the given email already exists.
 */
public class UserAlreadyExistsException extends UserRegistrationException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
