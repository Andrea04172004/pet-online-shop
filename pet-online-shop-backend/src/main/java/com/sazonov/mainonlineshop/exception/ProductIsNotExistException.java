package com.sazonov.mainonlineshop.exception;

// Exception could be thrown if we are trying to find product by name in a search bar

public class ProductIsNotExistException extends RuntimeException {
    public ProductIsNotExistException() {
        super();
    }

    public ProductIsNotExistException(String message) {
        super(message);
    }

    public ProductIsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIsNotExistException(Throwable cause) {
        super(cause);
    }
}
