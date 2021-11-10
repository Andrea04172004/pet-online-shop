package com.sazonov.mainonlineshop.mapper;

import com.sazonov.mainonlineshop.dto.CreditCardDto;
import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formdto.AddCardDtoRequest;
import com.sazonov.mainonlineshop.exception.CreditCardIsAlreadyExistException;
import com.sazonov.mainonlineshop.repository.CreditCardRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.userentity.CreditCardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CreditCardMapper {


    @Autowired
    private BusinessMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;


    public Function<CreditCardDto, CreditCardEntity> cardToEntity = p -> getCreditCardEntity(p);
    public Function<CreditCardEntity, CreditCardDto> cardToDto = p -> getCreditCardDto(p);

    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toList());
    }


    public CreditCardDto getCreditCardDtoToAddCard(AddCardDtoRequest addCardDtoRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDto userDto = userMapper.getUserDto(userRepository.findByEmail(auth.getName()));

        return CreditCardDto.builder()
                .cardNumber(addCardDtoRequest.getCardNumber())
                .expirationDate(addCardDtoRequest.getExpirationDate())
                .cardType(addCardDtoRequest.getCardType())
                .userDto(userDto)
                .build();

    }

    public CreditCardDto getCreditCardDto(CreditCardEntity creditCardEntity) {

        return CreditCardDto.builder()
                .id(creditCardEntity.getId())
                .cardNumber(creditCardEntity.getCardNumber())
                .expirationDate(creditCardEntity.getExpirationDate())
                .cardType(creditCardEntity.getCardType()).build();
    }

    public CreditCardEntity getCreditCardEntity(CreditCardDto creditCardDto) {
            return CreditCardEntity.builder()
                    .id(creditCardDto.getId())
                    .cardNumber(creditCardDto.getCardNumber())
                    .expirationDate(creditCardDto.getExpirationDate())
                    .cardType(creditCardDto.getCardType()).build();

    }

    public CreditCardEntity getCreditCardEntityForUpdate(CreditCardDto creditCardDto){

        CreditCardEntity creditCardEntity = creditCardRepository.findById(creditCardDto.getId());

        creditCardEntity.setCardNumber(creditCardDto.getCardNumber());
        creditCardEntity.setExpirationDate(creditCardDto.getExpirationDate());
        creditCardEntity.setCardType(creditCardDto.getCardType());

        return creditCardEntity;
    }

}


