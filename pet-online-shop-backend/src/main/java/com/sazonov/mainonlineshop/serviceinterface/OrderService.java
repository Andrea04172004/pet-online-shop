package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.OrderDto;
import com.sazonov.mainonlineshop.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders();

    List<OrderDto> getAllOrdersByStatus(OrderStatus orderStatus);

    OrderDto getOrderById(int id);

    List<OrderDto> getOrdersByUserEmail(String userEmail);

    List<OrderDto> getOrdersByUserPhone(String userPhoneNumber);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(OrderDto orderDto);

    void deleteOrder(int id);

}
