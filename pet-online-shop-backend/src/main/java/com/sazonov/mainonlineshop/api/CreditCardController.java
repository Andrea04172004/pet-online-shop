package com.sazonov.mainonlineshop.api;

import com.sazonov.mainonlineshop.dto.CreditCardDto;
import com.sazonov.mainonlineshop.dto.formdto.AddCardDtoRequest;
import com.sazonov.mainonlineshop.mapper.CreditCardMapper;
import com.sazonov.mainonlineshop.serviceinterface.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/credit-card")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardMapper creditCardMapper;


    @PostMapping("/add")
    public ResponseEntity<CreditCardDto> saveCard(@RequestBody AddCardDtoRequest addCardDtoRequest) {

        CreditCardDto creditCardDto = creditCardMapper.getCreditCardDtoToAddCard(addCardDtoRequest);

        return ResponseEntity.ok(creditCardService.saveCard(creditCardDto));

    }

    @GetMapping("/delete/{id}") //TEST
    public ResponseEntity<CreditCardDto> delete(@PathVariable("id") int id) {

        creditCardService.deleteCard(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CreditCardDto>> getAllCards() {

        return ResponseEntity.ok(creditCardService.getAllCards());

    }

    @GetMapping("/update/{id}")
    public ResponseEntity<CreditCardDto> getCreditCardForUpdate(@PathVariable("id") int id){
        return ResponseEntity.ok(creditCardService.getCreditCardById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<CreditCardDto> updateCreditCard(@RequestBody CreditCardDto creditCardDto){

        return ResponseEntity.ok(creditCardService.updateCreditCard(creditCardDto));
    }



}
