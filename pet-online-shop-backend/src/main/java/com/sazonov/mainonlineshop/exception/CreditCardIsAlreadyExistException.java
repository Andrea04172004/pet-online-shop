package com.sazonov.mainonlineshop.exception;

public class CreditCardIsAlreadyExistException extends RuntimeException {
    public CreditCardIsAlreadyExistException() {
        super();
    }

    public CreditCardIsAlreadyExistException(String message) {
        super(message);
    }

    public CreditCardIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreditCardIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
