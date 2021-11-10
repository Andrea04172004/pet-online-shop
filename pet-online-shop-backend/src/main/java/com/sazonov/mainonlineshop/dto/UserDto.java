package com.sazonov.mainonlineshop.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class UserDto {

    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;
    private List<String> phoneList = new ArrayList<>();
    private String role;
    private LocalDate created;
    private LocalDate updated;
    private LocalDate lastVisit;

    private Set<OrderDto> orders;

    private CartDto cartDto;

    private Set<CreditCardDto> creditCardDto;

    private Set<AddressDto> addressDtoSet;
    private Set<WishListDto> wishListDto;

    public List<String> addPhone(String phone) {
        this.phoneList.add(phone);
        return phoneList;
    }
}

