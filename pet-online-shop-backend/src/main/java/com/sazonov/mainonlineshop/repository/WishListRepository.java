package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishListEntity, Integer> {

    WishListEntity findByTitle(String title);
}
