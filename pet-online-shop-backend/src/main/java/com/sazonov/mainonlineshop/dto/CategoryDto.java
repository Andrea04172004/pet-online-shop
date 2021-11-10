package com.sazonov.mainonlineshop.dto;

import lombok.*;


import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class CategoryDto {

    private int id;
    private String name;
    private Set<ProductDto> productDtoSet;
    private CategoryDto parentCategory;

}
