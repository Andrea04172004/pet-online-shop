package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository <DiscountEntity, Integer> {
    DiscountEntity getById (int id);
}
