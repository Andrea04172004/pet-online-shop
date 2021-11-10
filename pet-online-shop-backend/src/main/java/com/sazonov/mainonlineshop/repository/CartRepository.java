package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
}
