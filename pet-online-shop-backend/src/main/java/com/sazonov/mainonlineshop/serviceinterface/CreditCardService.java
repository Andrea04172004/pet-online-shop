package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.CreditCardDto;

import java.util.List;

public interface CreditCardService {

    CreditCardDto saveCard(CreditCardDto creditCardDto);

    void deleteCard(int id);

    List<CreditCardDto> getAllCards();

    CreditCardDto updateCreditCard(CreditCardDto creditCardDto);

    CreditCardDto getCreditCardById (int id);

}
