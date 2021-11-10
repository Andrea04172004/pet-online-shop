package com.sazonov.mainonlineshop.exception;

public class WishListIsNotExistException extends RuntimeException{
    public WishListIsNotExistException() {
        super();
    }

    public WishListIsNotExistException(String message) {
        super(message);
    }

    public WishListIsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishListIsNotExistException(Throwable cause) {
        super(cause);
    }
}
