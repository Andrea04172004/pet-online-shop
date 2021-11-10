package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

   // CategoryEntity findById(int id);
    CategoryEntity findByName(String name);
    void deleteByName(String name);

}
