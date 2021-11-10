package com.sazonov.mainonlineshop.exception;

public class PaymentIsNotExistException extends RuntimeException{
    public PaymentIsNotExistException (){super();}
    public PaymentIsNotExistException (String message){super(message);}
    public PaymentIsNotExistException (String message, Throwable cause){super(message, cause);}
    public PaymentIsNotExistException (Throwable cause){super(cause);}
}
