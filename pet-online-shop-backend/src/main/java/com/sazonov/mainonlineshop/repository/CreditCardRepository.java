package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.userentity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardEntity, String> {

    CreditCardEntity findById(int id);

    CreditCardEntity findByCardNumber(String number);

}
