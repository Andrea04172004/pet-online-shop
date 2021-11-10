package com.sazonov.mainonlineshop.exception;

public class AttachmentIsNotExistException extends RuntimeException{
    public AttachmentIsNotExistException() {
        super();
    }

    public AttachmentIsNotExistException(String message) {
        super(message);
    }

    public AttachmentIsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentIsNotExistException(Throwable cause) {
        super(cause);
    }
}
