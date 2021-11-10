package com.sazonov.mainonlineshop.exception;

public class DiscountIsNotExistException extends RuntimeException{
    public DiscountIsNotExistException() {
        super();
    }

    public DiscountIsNotExistException(String message) {
        super(message);
    }

    public DiscountIsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountIsNotExistException(Throwable cause) {
        super(cause);
    }
}
