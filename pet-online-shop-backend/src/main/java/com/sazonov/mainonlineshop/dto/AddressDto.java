package com.sazonov.mainonlineshop.dto;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class AddressDto {

    private int id;
    private String city;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
}
