package com.sazonov.mainonlineshop.exception;

public class CategoryIsAlreadyExistException extends RuntimeException {
    public CategoryIsAlreadyExistException() {
        super();
    }

    public CategoryIsAlreadyExistException(String message) {
        super(message);
    }

    public CategoryIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
