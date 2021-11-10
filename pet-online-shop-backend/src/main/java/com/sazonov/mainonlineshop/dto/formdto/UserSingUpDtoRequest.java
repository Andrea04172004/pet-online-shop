package com.sazonov.mainonlineshop.dto.formdto;

import lombok.*;


@Data

public class UserSingUpDtoRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;


}
