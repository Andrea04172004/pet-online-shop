package com.sazonov.mainonlineshop.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartDto {

    private int id;

    private List<LineItemDto> lineItemDtoSet = new ArrayList<>();

    //private OrderDto orderDto;

    public CartDto(CartDto cartDto) {
    }


    public void addTo(LineItemDto itemDto) {

        this.lineItemDtoSet.add(itemDto);
    }


    public double countPrice() {

            double price = 0;

            for (int i = 0; i < lineItemDtoSet.size(); i++) {
                price += lineItemDtoSet.get(i).getSubPrice();
            }
            return price;
    }
}
