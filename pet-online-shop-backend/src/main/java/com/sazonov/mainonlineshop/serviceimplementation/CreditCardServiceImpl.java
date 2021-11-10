package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.CreditCardDto;
import com.sazonov.mainonlineshop.enums.ResultEnum;
import com.sazonov.mainonlineshop.exception.CreditCardIsAlreadyExistException;
import com.sazonov.mainonlineshop.mapper.CreditCardMapper;
import com.sazonov.mainonlineshop.repository.CreditCardRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.serviceinterface.CreditCardService;
import com.sazonov.mainonlineshop.userentity.CreditCardEntity;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private CreditCardMapper creditCardMapper;

    @Resource
    private CreditCardRepository creditCardRepository;


    public CreditCardDto saveCard(CreditCardDto creditCardDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        CreditCardEntity creditCardEntity = creditCardRepository.findByCardNumber(creditCardDto.getCardNumber());
        if(creditCardEntity != null){
            throw new CreditCardIsAlreadyExistException(ResultEnum.CREDIT_CARD_ALREADY_EXISTS.getMessage());
        }

        creditCardEntity = creditCardMapper.getCreditCardEntity(creditCardDto);
        creditCardRepository.save(creditCardEntity);

        userEntity.getCreditCardEntitySet().add(creditCardEntity);
        userRepository.save(userEntity);
        return creditCardMapper.getCreditCardDto(creditCardEntity);
    }


    public void deleteCard(int id) {

        CreditCardEntity creditCardEntity = creditCardRepository.findById(id);

        creditCardRepository.delete(creditCardEntity);

    }


    public List<CreditCardDto> getAllCards() {

        List<CreditCardEntity> creditCardEntityList = creditCardRepository.findAll();

        return creditCardMapper.collectionToList(creditCardEntityList, creditCardMapper.cardToDto);
    }

    public CreditCardDto updateCreditCard(CreditCardDto creditCardDto) {
        CreditCardEntity creditCardEntity = creditCardMapper.getCreditCardEntityForUpdate(creditCardDto);
        creditCardRepository.save(creditCardEntity);
        return creditCardMapper.getCreditCardDto(creditCardEntity);
    }

    public CreditCardDto getCreditCardById(int id) {
        CreditCardEntity creditCardEntity = creditCardRepository.findById(id);
        return creditCardMapper.getCreditCardDto(creditCardEntity);

    }
}
