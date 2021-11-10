package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.OrderDto;
import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.enums.OrderStatus;
import com.sazonov.mainonlineshop.enums.ResultEnum;
import com.sazonov.mainonlineshop.exception.OrderIsNotExistException;
import com.sazonov.mainonlineshop.exception.ProductIsNotExistException;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.repository.*;
import com.sazonov.mainonlineshop.serviceinterface.OrderService;
import com.sazonov.mainonlineshop.serviceinterface.ProductService;
import com.sazonov.mainonlineshop.shopentity.LineItemEntity;
import com.sazonov.mainonlineshop.shopentity.OrderEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AddressRepository addressRepository;

    @Resource
    private BusinessMapper businessMapper;

    @Resource
    private LineItemRepository lineItemRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductService productService;

    @Override
    public OrderDto getOrderById(int id) {

        if (orderRepository.findById(id) == null) throw new OrderIsNotExistException("Order is not exists");

        OrderEntity orderEntity = orderRepository.findById(id);

        return businessMapper.getOrderDto(orderEntity);

    }

    @Override
    public List<OrderDto> getAllOrders() {

        List<OrderEntity> orderEntityList = orderRepository.findAll();

        if (orderEntityList.isEmpty()) throw new OrderIsNotExistException("Orders are not exist");

        return businessMapper.collectionToList(orderEntityList, businessMapper.orderFromEntityToDto);
    }

    @Override
    public List<OrderDto> getAllOrdersByStatus(OrderStatus orderStatus) {

        List<OrderEntity> orderEntityList = orderRepository.findAllByStatus(orderStatus);

        if (orderEntityList.isEmpty()) throw new OrderIsNotExistException("Orders are not exist");

        return businessMapper.collectionToList(orderEntityList, businessMapper.orderFromEntityToDto);
    }


    @Override
    public List<OrderDto> getOrdersByUserEmail(String userEmail) {

        List<OrderEntity> orderEntityList = orderRepository.findAllByUserEntityEmail(userEmail);

        if (orderEntityList.isEmpty()) throw new OrderIsNotExistException("Orders are not exist");

        return businessMapper.collectionToList(orderEntityList, businessMapper.orderFromEntityToDto);
    }

    @Override
    public List<OrderDto> getOrdersByUserPhone(String userPhoneNumber) {

        List<OrderEntity> orderEntityList = orderRepository.findAllByUserEntityPhoneList(userPhoneNumber);

        if (orderEntityList.isEmpty()) throw new OrderIsNotExistException("Orders are not exist");

        return businessMapper.collectionToList(orderEntityList, businessMapper.orderFromEntityToDto);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }

        OrderEntity orderEntity = businessMapper.getOrderEntity(orderDto);

        /*--- proverka nalichie tovarov na sklade ---*/
        for(LineItemEntity lineItem : orderEntity.getLineItemEntitySet()){

            ProductEntity productEntity = productRepository.findById(lineItem.getProduct().getId());

            if(lineItem.getProduct().getQuantity() <= productEntity.getQuantity())
                throw new ProductIsNotExistException("This product " + productEntity.getName()
                                                    + "isn't available");
        }

        orderRepository.save(orderEntity);

        /*spisanie produktov so sklada*/
        for(LineItemEntity lineItemEntity : orderEntity.getLineItemEntitySet()){

            //product na sklade
            ProductEntity productEntity = productRepository.findById(lineItemEntity.getProduct().getId());

            int newQuantity = productEntity.getQuantity() - lineItemEntity.getProduct().getQuantity();

            productEntity.setQuantity(newQuantity);
            productRepository.save(productEntity);

        }

        userEntity.getOrderEntitySet().add(orderEntity);
        userRepository.save(userEntity);

        return businessMapper.getOrderDto(orderEntity);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.findById(orderDto.getId());

        if(orderEntity == null){
            throw new OrderIsNotExistException(ResultEnum.ORDER_NOT_EXISTS.getMessage());
        }

        if(orderDto.getCreated() != null){
            orderEntity.setCreated(orderDto.getCreated());
        }
        if(orderDto.getAddressDto() != null){
            orderEntity.setAddressEntity(businessMapper.getAddressEntity(orderDto.getAddressDto()));
        }
        if(orderDto.getDiscountDto() != null){
            orderEntity.setDiscountEntity(businessMapper.getDiscountEntity(orderDto.getDiscountDto()));
        }
        if(orderDto.getOrderPrice() != 0){
            orderEntity.setOrderPrice(orderDto.getOrderPrice());
        }
        if(orderDto.getStatus() != null){
            orderEntity.setStatus(OrderStatus.valueOf(orderDto.getStatus()));
        }

        orderRepository.save(orderEntity);
        return businessMapper.getOrderDto(orderEntity);
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
