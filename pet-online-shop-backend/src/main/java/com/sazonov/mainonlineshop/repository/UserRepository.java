package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    UserEntity findById(int id);

    List<UserEntity> findAllByEmailContaining(String email);

    @Query(value = "select count(*) from user_phone_numbers where phone_number = :phoneNumber", nativeQuery = true)
    int countPhones(@Param("phoneNumber") String phoneNumber);


}
