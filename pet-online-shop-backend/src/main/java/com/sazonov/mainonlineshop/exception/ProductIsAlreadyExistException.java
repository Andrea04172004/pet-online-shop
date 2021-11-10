package com.sazonov.mainonlineshop.exception;

// Exception could be thrown when attempted save product which is exist in DB already

public class ProductIsAlreadyExistException extends RuntimeException {
    public ProductIsAlreadyExistException() {
        super();
    }

    public ProductIsAlreadyExistException(String message) {
        super(message);
    }

    public ProductIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
