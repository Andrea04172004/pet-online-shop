package com.sazonov.mainonlineshop.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttachmentDto {

    private int id;

    private String name;

    private LocalDateTime created;

    private String content;
}
