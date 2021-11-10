package com.sazonov.mainonlineshop.dto;

import com.sazonov.mainonlineshop.enums.ProductLabel;
import com.sun.xml.bind.v2.TODO;
import lombok.*;


import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ProductDto {

    private int id;

    private String name;

    private double price;

    private List<String> image;

    private LocalDate expirationDate;

    private int quantity;

    private String category;

    private String productLabel;
}
