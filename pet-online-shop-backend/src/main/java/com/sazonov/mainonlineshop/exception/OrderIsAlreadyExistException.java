package com.sazonov.mainonlineshop.exception;

public class OrderIsAlreadyExistException extends RuntimeException{
    public OrderIsAlreadyExistException() {
        super();
    }

    public OrderIsAlreadyExistException(String message) {
        super(message);
    }

    public OrderIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
