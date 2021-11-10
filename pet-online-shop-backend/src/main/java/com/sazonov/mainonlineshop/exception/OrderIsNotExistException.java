package com.sazonov.mainonlineshop.exception;

public class OrderIsNotExistException extends RuntimeException{
    public OrderIsNotExistException() {
        super();
    }

    public OrderIsNotExistException(String message) {
        super(message);
    }

    public OrderIsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderIsNotExistException(Throwable cause) {
        super(cause);
    }
}
