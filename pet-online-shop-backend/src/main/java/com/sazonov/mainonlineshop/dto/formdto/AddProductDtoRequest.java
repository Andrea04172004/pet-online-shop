package com.sazonov.mainonlineshop.dto.formdto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class AddProductDtoRequest {

    private String name;
    private double price;
    private String expirationDate;
    private List<String> image;
    private int quantity;
    private String category;
    private String productLabel;
}
