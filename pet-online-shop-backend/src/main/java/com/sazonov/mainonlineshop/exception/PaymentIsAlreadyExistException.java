package com.sazonov.mainonlineshop.exception;

public class PaymentIsAlreadyExistException extends RuntimeException{
    public PaymentIsAlreadyExistException(){super();}
    public PaymentIsAlreadyExistException(String message){super(message);}
    public PaymentIsAlreadyExistException(String message, Throwable cause){super(message, cause);}
    public PaymentIsAlreadyExistException(Throwable cause){super(cause);}
}
