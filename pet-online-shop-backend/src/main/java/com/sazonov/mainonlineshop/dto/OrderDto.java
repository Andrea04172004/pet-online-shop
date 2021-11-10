package com.sazonov.mainonlineshop.dto;


import com.sazonov.mainonlineshop.shopentity.AttachmentEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class OrderDto {

    private int id;

    private String status;

    private List<LineItemDto> lineItemDtoSet;

    private double orderPrice;

    private UserShortResponseDto userShortResponseDto;

    private LocalDate created;

    private AddressDto addressDto;

    private List<PaymentDto> paymentsDto;

    private DiscountDto discountDto;
}
