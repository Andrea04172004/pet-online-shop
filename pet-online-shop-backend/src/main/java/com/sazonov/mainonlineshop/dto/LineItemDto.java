package com.sazonov.mainonlineshop.dto;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class LineItemDto {

    private int id;
    private int quantity;
    private ProductDto product;


    public double getSubPrice() {

        return product.getPrice()*quantity;

    }



}
