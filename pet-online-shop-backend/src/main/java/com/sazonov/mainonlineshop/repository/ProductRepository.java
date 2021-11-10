package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.dto.CategoryDto;
import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    ProductEntity findByName(String name);

    Set<ProductEntity> findAllByNameContains(String name);

    ProductEntity findById(int id);

    ProductEntity findOneByNameContains (String name);

    List<ProductEntity> findAllByCategory(CategoryEntity categoryEntity);

    List<ProductEntity> findAllByCategoryName(String name);
}
