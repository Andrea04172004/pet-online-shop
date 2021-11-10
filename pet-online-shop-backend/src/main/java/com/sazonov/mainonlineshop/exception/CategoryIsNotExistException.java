package com.sazonov.mainonlineshop.exception;

// Exception could be thrown if we are trying to find a category

public class CategoryIsNotExistException extends RuntimeException {
    public CategoryIsNotExistException() {
        super();
    }

    public CategoryIsNotExistException(String message) {
        super(message);
    }

    public CategoryIsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryIsNotExistException(Throwable cause) {
        super(cause);
    }
}
