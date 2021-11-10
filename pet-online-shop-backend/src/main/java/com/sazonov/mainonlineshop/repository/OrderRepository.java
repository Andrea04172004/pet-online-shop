package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.OrderEntity;
import com.sazonov.mainonlineshop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    OrderEntity findById(int id);

    List<OrderEntity> findAllByStatus(OrderStatus orderStatus);

    List<OrderEntity> findAllByUserEntityEmail(String email);

    List<OrderEntity> findAllByUserEntityPhoneList(String phoneNumber);

    void deleteById(int id);

}
