package com.sazonov.mainonlineshop.dto;

import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import lombok.*;


import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishListDto {
    private int id;
    private String title;
    private List<ProductDto> productDto;
}
