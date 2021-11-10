package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository <PaymentEntity, Long> {
    PaymentEntity getById (Long id);
    List<PaymentEntity> getAllById (Long id);
}
