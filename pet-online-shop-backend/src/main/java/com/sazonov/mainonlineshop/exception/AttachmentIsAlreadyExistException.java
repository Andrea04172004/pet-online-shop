package com.sazonov.mainonlineshop.exception;

public class AttachmentIsAlreadyExistException extends RuntimeException{
    public AttachmentIsAlreadyExistException() {
        super();
    }

    public AttachmentIsAlreadyExistException(String message) {
        super(message);
    }

    public AttachmentIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
