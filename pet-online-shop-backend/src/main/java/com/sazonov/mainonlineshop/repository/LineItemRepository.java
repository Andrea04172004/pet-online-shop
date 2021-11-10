package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.LineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends JpaRepository<LineItemEntity, Integer> {

}
