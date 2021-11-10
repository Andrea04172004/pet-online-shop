package com.sazonov.mainonlineshop.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class UserShortResponseDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phone;
}
