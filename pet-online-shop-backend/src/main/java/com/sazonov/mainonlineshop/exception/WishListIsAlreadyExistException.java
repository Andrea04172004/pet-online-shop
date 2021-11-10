package com.sazonov.mainonlineshop.exception;

public class WishListIsAlreadyExistException extends RuntimeException{
    public WishListIsAlreadyExistException() {
        super();
    }

    public WishListIsAlreadyExistException(String message) {
        super(message);
    }

    public WishListIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishListIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
