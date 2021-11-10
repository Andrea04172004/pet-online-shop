package com.sazonov.mainonlineshop.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10, "Product doesn't exist!"),
    PRODUCT_ALREADY_EXISTS(11, "Product already exists!"),

    CATEGORY_NOT_EXIST(20, "Category doesn't exist!"),
    CATEGORY_ALREADY_EXISTS(21, "Category already exists!"),

    CREDIT_CARD_NOT_EXIST(30, "Credit card doesn't exist!"),
    CREDIT_CARD_ALREADY_EXISTS(31, "Credit card already exists!"),

    USER_NOT_EXIST(40, "User doesn't exist!"),
    USER_ALREADY_EXISTS(41, "User already exists!"),

    WRONG_PASSWORD(50, "The password is wrong!"),

    ORDER_NOT_EXISTS (60, "Order doesn't exist!"),
    ORDER_ALREADY_EXISTS (61, "Order with already exist"),

    PAYMENT_NOT_EXISTS (70, "Payment doesn't exist!"),
    PAYMENT_ALREADY_EXISTS (71, "Payment already exist!"),

    BIG_QUANTITY (81,"This quantity is not available now"),
    AVAILABLE_QUANTITY (82, "This quantity is available");
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
