package com.sazonov.mainonlineshop.exception;

public class UserIsAlreadyExistException extends RuntimeException {
    public UserIsAlreadyExistException() {
        super();
    }

    public UserIsAlreadyExistException(String message) {
        super(message);
    }

    public UserIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
