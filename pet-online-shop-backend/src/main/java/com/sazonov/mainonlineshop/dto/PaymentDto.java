package com.sazonov.mainonlineshop.dto;

import com.sazonov.mainonlineshop.shopentity.AttachmentEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String amount;
    private boolean isCash;
    private String clientName;
    private AttachmentDto attachmentDto;
}
