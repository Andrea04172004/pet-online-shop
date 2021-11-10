package com.sazonov.mainonlineshop.mapper;


import com.sazonov.mainonlineshop.dto.*;
import com.sazonov.mainonlineshop.dto.formdto.AddProductDtoRequest;
import com.sazonov.mainonlineshop.dto.formdto.UserSingUpDtoRequest;
import com.sazonov.mainonlineshop.enums.OrderStatus;
import com.sazonov.mainonlineshop.enums.ProductLabel;
import com.sazonov.mainonlineshop.exception.ProductIsAlreadyExistException;
import com.sazonov.mainonlineshop.exception.UserIsAlreadyExistException;
import com.sazonov.mainonlineshop.repository.*;
import com.sazonov.mainonlineshop.shopentity.*;
import com.sazonov.mainonlineshop.userentity.AddressEntity;
import com.sazonov.mainonlineshop.enums.Roles;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BusinessMapper {
    @Resource
    private CategoryRepository categoryRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CartRepository cartRepository;
    @Resource
    private AddressRepository addressRepository;
    @Resource
    private DiscountRepository discountRepository;
    @Resource
    private CreditCardMapper creditCardMapper;


    public Function<ProductEntity, ProductDto> productToDto = this::getProductDto;
    public Function<ProductDto, ProductEntity> productToEntity = this::getProductEntityToAddProduct;
    public Function<ProductDto, ProductEntity> productForListToEntity = this::getProductEntity;

    public Function<CategoryEntity, CategoryDto> categoryToDto = this::getCategoryDto;
    public Function<CategoryDto, CategoryEntity> categoryToEntity = this::getCategoryEntity;

    public Function<UserEntity, UserDto> userToDto = this::getUserDto;
    public Function<UserDto, UserEntity> userToEntity = this::getUserEntity;

    public Function<AddressEntity, AddressDto> addressToDto = this::getAddressDto;
    public Function<AddressDto, AddressEntity> addressToEntity = this::getAddressEntity;

    public Function<PaymentEntity, PaymentDto> paymentToDto = this::convertToPaymentDto;
    public Function<PaymentDto, PaymentEntity> paymentToEntity = this::convertToPaymentEntity;

    public Function<AttachmentEntity, AttachmentDto> attachmentFromEntityToDto = this::getAttachmentDto;
    public Function<AttachmentDto, AttachmentEntity> attachmentFromDtoToEntity = this::getAttachmentEntity;

    public Function<LineItemEntity, LineItemDto> lineItemToDto = this::getLineItemDto;
    public Function<LineItemDto, LineItemEntity> lineItemToEntity = this::getLineItemEntity;

    public Function<OrderEntity, OrderDto> orderFromEntityToDto = this::getOrderDto;
    public Function<OrderDto, OrderEntity> orderFromDtoToEntity = this::getOrderEntity;

    public Function<DiscountEntity, DiscountDto> discountFromEntityToDto = this::getDiscountDto;
    public Function<DiscountDto, DiscountEntity> discountFromDtoToEntity = this::getDiscountEntity;

    public Function<WishListEntity, WishListDto> wishListFromEntityToDto = this::getWishListDto;
    public Function<WishListDto, WishListEntity> wishListFromDtoToEntity = this::getWishListEntity;


    public <A, R> Set<R> collectionToSet(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }


    public ProductDto getProductDtoToAddProduct(AddProductDtoRequest addProductDtoRequest) {

        return ProductDto.builder()
                .name(addProductDtoRequest.getName())
                .price(addProductDtoRequest.getPrice())
                .image(addProductDtoRequest.getImage())
                .expirationDate(LocalDate.parse(addProductDtoRequest.getExpirationDate()))
                .quantity(addProductDtoRequest.getQuantity())
                .productLabel(addProductDtoRequest.getProductLabel())
                .category(addProductDtoRequest.getCategory())
                .build();
    }

    //TODO --> NEXT REVIEW
    public ProductEntity getProductEntityForOrder(ProductDto productDto) {

        return ProductEntity.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .image(productDto.getImage())
                .productLabel(ProductLabel.valueOf(productDto.getProductLabel()))
                .quantity(productDto.getQuantity())
                .expirationDate(LocalDate.now().plusDays(100))
                .category(Optional.ofNullable(categoryRepository.findByName(productDto.getCategory())).orElse(new CategoryEntity(productDto.getCategory())))

                .build();
    }

    //FIXME BUG-N-181220-01 WishList during mapping throws exc line 119!
    public ProductEntity getProductEntityToAddProduct(ProductDto productDto) {

        ProductEntity productEntity = productRepository.findByName(productDto.getName());

//        if (productEntity != null) {
//            throw new ProductIsAlreadyExistException("Product with this name is already exist in DB");
//        } else

            return ProductEntity.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .image(productDto.getImage())
                    .productLabel(ProductLabel.valueOf(productDto.getProductLabel()))
                    .quantity(productDto.getQuantity())
                    .expirationDate(productDto.getExpirationDate())
                    .category(Optional.ofNullable(categoryRepository.findByName(productDto.getCategory())).orElse(new CategoryEntity(productDto.getCategory())))

                    .build();
    }

    public ProductEntity getProductEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .image(productDto.getImage())
                .productLabel(ProductLabel.valueOf(productDto.getProductLabel()))
                .quantity(productDto.getQuantity())
                .expirationDate(productDto.getExpirationDate())
                .category(Optional.ofNullable(categoryRepository.findByName(productDto.getCategory())).orElse(new CategoryEntity(productDto.getCategory())))
                .build();
    }

    public ProductDto getProductDto(ProductEntity productEntity) {

        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .image(productEntity.getImage())
                .price(productEntity.getPrice())
                .quantity(productEntity.getQuantity())
                .productLabel(productEntity.getProductLabel().toString())
                .expirationDate(productEntity.getExpirationDate())
                .category(productEntity.getCategory().getName())

                .build();
    }

    public ProductEntity getProductEntityForUpdate(ProductDto productDto) {

        ProductEntity productEntity = productRepository.findById(productDto.getId());

        productEntity.setName(productDto.getName());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setImage(productDto.getImage());
        productEntity.setProductLabel(ProductLabel.valueOf(productDto.getProductLabel()));
        productEntity.setQuantity(productDto.getQuantity());
        productEntity.setExpirationDate(productDto.getExpirationDate());
        productEntity.setCategory(Optional.ofNullable(categoryRepository.findByName(productDto.getCategory())).orElse(new CategoryEntity(productDto.getCategory())));


        return productEntity;

    }


    public CategoryEntity getCategoryEntity(CategoryDto categoryDto) {

        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .productSet(collectionToSet(categoryDto.getProductDtoSet(), this::getProductEntityToAddProduct))
                //.parentCategory(getCategoryEntity(categoryDto.getParentCategory()))
                //FIXME Recursive call parent category
                .build();

    }

    public CategoryDto getCategoryDto(CategoryEntity categoryEntity) {

        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .productDtoSet(collectionToSet(categoryEntity.getProductSet(), productToDto))
                //.parentCategory(getCategoryDto(categoryEntity.getParentCategory()))
                //FIXME Recursive call parent category

                .build();

    }

    public UserDto getUserDtoForSignUp(UserSingUpDtoRequest userSingUpDto) {

        UserDto userDto = new UserDto();

        return UserDto.builder()
                .email(userSingUpDto.getEmail())
                .password(userSingUpDto.getPassword())
                .firstName(userSingUpDto.getFirstName())
                .lastName(userSingUpDto.getLastName())
                .phoneList(userDto.addPhone(userSingUpDto.getPhone().replaceAll("[^\\d]", "")))
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .lastVisit(LocalDate.now())
                .role(Roles.ROLE_CUSTOMER.name())
                .build();
    }

    public UserEntity getUserEntityForSignUp(UserDto userDto) {

        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());

        if (userEntity != null) {
            throw new UserIsAlreadyExistException("User is already exist");
        } else
            return UserEntity.builder()
                    .id(userDto.getId())
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .phoneList(userDto.getPhoneList())
                    .role(userDto.getRole())
                    .created(userDto.getCreated())
                    .updated(userDto.getUpdated())
                    .lastVisit(userDto.getLastVisit())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .active(true)

                    .build();
    }


    public UserEntity getUserEntity(UserDto userDto) { //in use

        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .addressEntitySet(collectionToSet(userDto.getAddressDtoSet(), addressToEntity))
                .phoneList(userDto.getPhoneList())
                .role(userDto.getRole())
                .active(userDto.isActive())
                .created(userDto.getCreated())
                .updated(userDto.getUpdated())
                .lastVisit(userDto.getLastVisit())

                .cartEntity(getCartEntity(userDto.getCartDto()))
                .creditCardEntitySet(collectionToSet(userDto.getCreditCardDto(), creditCardMapper.cardToEntity))

                .role(userDto.getRole())
                //.orderEntitySet(collectionToSet(userDto.getOrders(), orderFromDtoToEntity))
                .wishListEntities(collectionToSet(userDto.getWishListDto(), wishListFromDtoToEntity))

                .build();
    }

    public UserDto getUserDto(UserEntity userEntity) {//in use


        return UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .active(userEntity.isActive())//fixme check it
                .addressDtoSet(collectionToSet(userEntity.getAddressEntitySet(), addressToDto))
                .phoneList(userEntity.getPhoneList())
                .created(userEntity.getCreated())
                .updated(userEntity.getUpdated())
                .lastVisit(userEntity.getLastVisit())

                .cartDto(getCartDto(userEntity.getCartEntity()))
                .creditCardDto(collectionToSet(userEntity.getCreditCardEntitySet(), creditCardMapper.cardToDto))

                .role(userEntity.getRole())
                .orders(collectionToSet(userEntity.getOrderEntitySet(), orderFromEntityToDto))
                .wishListDto(collectionToSet(userEntity.getWishListEntities(), wishListFromEntityToDto))
                .build();
    }


    public AddressEntity getAddressEntity(AddressDto addressDto) { //in use

        return AddressEntity.builder()
                .id(addressDto.getId())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .buildingNumber(addressDto.getBuildingNumber())
                .apartmentNumber(addressDto.getApartmentNumber())
                .build();
    }

    public AddressDto getAddressDto(AddressEntity addressEntity) { // in use

        return AddressDto.builder()
                .id(addressEntity.getId())
                .city(addressEntity.getCity())
                .street(addressEntity.getStreet())
                .buildingNumber(addressEntity.getBuildingNumber())
                .apartmentNumber(addressEntity.getApartmentNumber())
                .build();
    }


    public CartEntity getCartEntity(CartDto cartDto) {

        return CartEntity.builder()
                .id(cartDto.getId())
                .lineItemEntitySet(collectionToSet(cartDto.getLineItemDtoSet(), this::getLineItemEntity))
                .build();
    }

    public CartDto getCartDto(CartEntity cartEntity) {

        return CartDto.builder()
                .id(cartEntity.getId())
                .lineItemDtoSet(collectionToList(cartEntity.getLineItemEntitySet(), this::getLineItemDto))
                .build();
    }


    //   lineItem
    public LineItemDto getLineItemDto(LineItemEntity lineItemEntity) {

        return LineItemDto.builder()
                .id(lineItemEntity.getId())
                .product(getProductDto(lineItemEntity.getProduct()))
                .quantity(lineItemEntity.getQuantity())
                .build();
    }

    public LineItemEntity getLineItemEntity(LineItemDto lineItemDto) {

        return LineItemEntity.builder()
                .id(lineItemDto.getId())
                .product(getProductEntityForOrder(lineItemDto.getProduct()))
                .quantity(lineItemDto.getQuantity())
                .build();
    }


    //   order
    public OrderDto getOrderDto(OrderEntity orderEntity) {

        //UserDto userDto = userMapper.getUserDto(orderEntity.getUserEntity()); //fixme
        AddressDto addressDto = getAddressDto(orderEntity.getAddressEntity());
        DiscountDto discountDto = getDiscountDto(orderEntity.getDiscountEntity());

        return OrderDto.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus().toString())
                .created(orderEntity.getCreated())
                .orderPrice(orderEntity.getOrderPrice())
                .lineItemDtoSet(collectionToList(orderEntity.getLineItemEntitySet(), lineItemToDto))
                .userShortResponseDto(UserShortResponseDto.builder()
                        .id(orderEntity.getUserEntity().getId())
                        .firstName(orderEntity.getUserEntity().getFirstName())
                        .lastName(orderEntity.getUserEntity().getLastName())
                        .email(orderEntity.getUserEntity().getEmail())
//                        .phoneList(userDto.getPhoneList())
                        .build())
                .addressDto(addressDto)
                .paymentsDto(collectionToList(orderEntity.getPaymentEntity(), paymentToDto))
                .discountDto(discountDto)
                .build();
    }


    public OrderEntity getOrderEntity(OrderDto orderDto) {//FIXME

        UserEntity userEntity = userRepository.findById(orderDto.getUserShortResponseDto().getId());
        AddressEntity addressEntity = addressRepository.findById(orderDto.getAddressDto().getId());
        DiscountEntity discountEntity = discountRepository.getById(orderDto.getDiscountDto().getId());

        return OrderEntity.builder()
                .id(orderDto.getId())
                .status(OrderStatus.valueOf(orderDto.getStatus()))
                .created(orderDto.getCreated())
                .lineItemEntitySet(collectionToList(orderDto.getLineItemDtoSet(), lineItemToEntity))
                .orderPrice(orderDto.getOrderPrice())
                .userEntity(userEntity)
                .addressEntity(addressEntity)
                .paymentEntity(collectionToList(orderDto.getPaymentsDto(), paymentToEntity))
                .discountEntity(discountEntity)
                .build();
    }

    //payment
    public PaymentDto convertToPaymentDto(PaymentEntity paymentEntity) {
        return PaymentDto.builder()
                .id(paymentEntity.getId())
                .clientName(paymentEntity.getClientName())
                .amount(paymentEntity.getAmount())
                .date(paymentEntity.getDateTime().toLocalDate())
                .time(paymentEntity.getDateTime().toLocalTime())
                .isCash(paymentEntity.isCash()).build();
    }

    public PaymentEntity convertToPaymentEntity(PaymentDto paymentDto) {
        return PaymentEntity.builder()
                .id(paymentDto.getId())
                .amount(paymentDto.getAmount())
                .clientName(paymentDto.getClientName())
                .dateTime(LocalDateTime.of(paymentDto.getDate(), paymentDto.getTime()))
                .isCash(paymentDto.isCash()).build();
    }

    //attachment
    public AttachmentEntity getAttachmentEntity(AttachmentDto attachmentDto) {

        return AttachmentEntity.builder()
                .id(attachmentDto.getId())
                .name(attachmentDto.getName())
                .created(attachmentDto.getCreated())
                .content(attachmentDto.getContent())
                .build();

    }


    public AttachmentDto getAttachmentDto(AttachmentEntity attachmentEntity) {

        return AttachmentDto.builder()
                .id(attachmentEntity.getId())
                .name(attachmentEntity.getName())
                .created(attachmentEntity.getCreated())
                .content(attachmentEntity.getContent())
                .build();

    }

    //discount

    public DiscountEntity getDiscountEntity(DiscountDto discountDto) {

        return DiscountEntity.builder()
                .id(discountDto.getId())
                .percent(discountDto.getPercent())
                .expiration(discountDto.getExpiration())
                .build();

    }

    public DiscountDto getDiscountDto(DiscountEntity discountEntity) {

        return DiscountDto.builder()
                .id(discountEntity.getId())
                .percent(discountEntity.getPercent())
                .expiration(discountEntity.getExpiration())
                .build();

    }

    public UserEntity getUserEntityForUpdate(UserEntity userEntityFromDB, UserDto userDto) { //in use

        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .active(userEntityFromDB.isActive())
                .addressEntitySet(collectionToSet(userDto.getAddressDtoSet(), addressToEntity))
                .phoneList(userDto.getPhoneList())
                .role(userDto.getRole())
                .created(userDto.getCreated())
                .updated(LocalDate.now())
                .lastVisit(LocalDate.now())//todo fixme
                // .orderEntitySet(collectionToSet(userDto.getOrders(), orderFromDtoToEntity))
                .cartEntity(userEntityFromDB.getCartEntity())

                .build();

    }

    public WishListEntity getWishListEntity(WishListDto wishListDto) {
        return WishListEntity.builder()
                .id(wishListDto.getId())
                .productEntities(collectionToList(wishListDto.getProductDto(), productToEntity))
                .title(wishListDto.getTitle()).build();

    }

    public WishListDto getWishListDto(WishListEntity wishListEntity) {
        return WishListDto.builder()
                .id(wishListEntity.getId())
                .productDto(collectionToList(wishListEntity.getProductEntities(), productToDto))
                .title(wishListEntity.getTitle()).build();
    }
}
