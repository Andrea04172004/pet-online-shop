package com.sazonov.mainonlineshop.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter


public class CreditCardDto {

    private int id;

    private String cardNumber;

    private String expirationDate;

    private String cardType;

    private UserDto userDto;
}
