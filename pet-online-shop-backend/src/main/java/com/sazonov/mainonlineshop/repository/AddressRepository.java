package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.userentity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

    AddressEntity findById(int id);

}
