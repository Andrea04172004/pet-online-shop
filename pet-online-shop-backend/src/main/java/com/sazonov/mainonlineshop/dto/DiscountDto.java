package com.sazonov.mainonlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DiscountDto {

    private int id;

    private double percent;

    private LocalDateTime expiration;

}
