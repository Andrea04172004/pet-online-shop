package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.*;
import com.sazonov.mainonlineshop.enums.ResultEnum;
import com.sazonov.mainonlineshop.exception.*;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.repository.*;
import com.sazonov.mainonlineshop.serviceinterface.*;
import com.sazonov.mainonlineshop.shopentity.*;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private BusinessMapper businessMapper;

    @Resource
    private LineItemRepository lineItemRepository;

    @Resource
    private CartRepository cartRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private PaymentRepository paymentRepository;

    @Resource
    private AttachmentRepository attachmentRepository;

    @Resource
    private DiscountRepository discountRepository;


    @Resource
    private WishListRepository wishListRepository;


    @Override
    public LineItemDto addLineItemToCart(int productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        CartEntity cartEntity = userEntity.getCartEntity();
        ProductEntity productEntity = productRepository.findById(productId);

        LineItemEntity lineItemEntity = LineItemEntity.builder()
                .quantity(1)
                .product(productEntity)
                .build();
        lineItemRepository.save(lineItemEntity);
        System.out.println("lineItemEntity---> " + lineItemEntity);
        cartEntity.getLineItemEntitySet().add(lineItemEntity);

        cartRepository.save(cartEntity);

        return businessMapper.getLineItemDto(lineItemEntity);
    }


    @Override
    public LineItemDto deleteByLineFromCart(int lineId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        CartEntity cartEntity = userEntity.getCartEntity();

        LineItemEntity lineItemEntity = lineItemRepository.findById(lineId).orElseThrow();

        cartEntity.getLineItemEntitySet().remove(lineItemEntity);

        cartRepository.save(cartEntity);

      return businessMapper.getLineItemDto(lineItemEntity);
    }

    @Override
    public LineItemDto deleteByProductFromCart(int productId) {

        LineItemEntity lineItemEntity = new LineItemEntity();

        System.out.println("productId - service---> " + productId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        CartEntity cartEntity = userEntity.getCartEntity();

        Set<LineItemEntity> lineItemsToDelete = new HashSet<>();

        Set<LineItemEntity> lineItemEntitySet = cartEntity.getLineItemEntitySet();

        for (LineItemEntity item : lineItemEntitySet) {
            if (item.getProduct().getId() == productId) {
                lineItemsToDelete.add(item);
            }
            lineItemEntity = item;
        }

        cartEntity.getLineItemEntitySet().removeAll(lineItemsToDelete);

        cartRepository.save(cartEntity);
        lineItemRepository.deleteAll(lineItemsToDelete);

        return businessMapper.getLineItemDto(lineItemEntity);
    }


    @Override
    public void deleteLineItemFromCart(int lineId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        CartEntity cartEntity = userEntity.getCartEntity();

        LineItemEntity deletedLine = lineItemRepository.findById(lineId).orElseThrow();
        System.out.println("deletedLine---> " + deletedLine);
        cartEntity.getLineItemEntitySet().remove(deletedLine);
        lineItemRepository.delete(deletedLine);
        cartRepository.save(cartEntity);
    }


    @Override
    public LineItemDto updateLineQuantity(int lineId, int quantity) {
        LineItemEntity lineItemEntity = lineItemRepository.findById(lineId).orElseThrow();
        lineItemEntity.setQuantity(quantity);
        lineItemRepository.save(lineItemEntity);
        return businessMapper.getLineItemDto(lineItemEntity);
    }

    @Override
    public CartDto getUserCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        CartEntity cartEntity = userEntity.getCartEntity();
        return businessMapper.getCartDto(cartEntity);
    }


    public CartDto addProductToCart(int id, int quantity) {
        ProductEntity productEntity = productRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        CartEntity cartEntity = userEntity.getCartEntity();

        List<LineItemEntity> items = cartEntity.getLineItemEntitySet().stream()
                .filter(item -> item.getProduct().getId() == productEntity.getId())
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            LineItemEntity lineItemEntity = LineItemEntity.builder()
                    .quantity(quantity)
                    .product(productEntity)
                    .build();
            lineItemRepository.save(lineItemEntity);
            cartEntity.getLineItemEntitySet().add(lineItemEntity);
        } else if (items.size() == 1) {
            items.get(0).increaseAmount();
        }

        cartRepository.save(cartEntity);
        return businessMapper.getCartDto(cartEntity);
    }


//    public OrderDto placeOrder() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        UserEntity userEntity = userRepository.findByEmail(auth.getName());
//
//        UserDto userDto = userMapper.getUserDto(userEntity);
//
//        OrderDto orderDto = OrderDto.builder()
//                .lineItemDtoSet(userDto.getCartDto().getLineItemDtoSet())
//                .userShortResponseDto(UserShortResponseDto.builder()
//                        .id(userDto.getId())
//                        .firstName(userDto.getFirstName())
//                        .lastName(userDto.getLastName())
//                        .email(userDto.getEmail())
//                        .phoneList(userDto.getPhoneList())
//                        .build())
//                .created(LocalDate.now())
//                .orderPrice(userDto.getCartDto().countPrice())
//                .status(OrderStatus.NEW.name())
//                .build();
//
//        OrderEntity orderEntity = shopMapper.getOrderEntity(orderDto);
//        orderRepository.save(orderEntity);
//
//        CartEntity cartEntity = shopMapper.getCartEntity(userDto.getCartDto());
//        cartEntity.getLineItemEntitySet().clear();
//        cartRepository.save(cartEntity);
//
//        return shopMapper.getOrderDto(orderEntity);
//    }

    //category
//    public CategoryDto saveCategory(CategoryDto categoryDto) {
//
//        CategoryEntity categoryEntity = shopMapper.getCategoryEntityToSave(categoryDto);
//
//        categoryRepository.save(categoryEntity);
//
//        return shopMapper.getCategoryDto(categoryEntity);
//    }
//
//
//    public CategoryDto getCategory(String name) {
//
//        CategoryEntity categoryEntity = Optional.ofNullable(categoryRepository.findByName(name)).orElse(null);
//
//        return shopMapper.getCategoryDto(categoryEntity);
//
//    }
//
//    @Override
//    public List<CategoryDto> findAllCategories() {
//        return productMapper.collectionToList(categoryRepository.findAll(), productMapper.categoryToDto);
//    }


    //========================payment===========================================

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto, int orderId) {
        PaymentEntity paymentEntity = paymentRepository.getById(paymentDto.getId());
        OrderEntity orderEntity = orderRepository.findById(orderId);

        if (paymentEntity != null) {
            throw new PaymentIsAlreadyExistException(ResultEnum.PAYMENT_ALREADY_EXISTS.getMessage());
        }
        if (orderEntity == null) {
            throw new OrderIsNotExistException(ResultEnum.ORDER_NOT_EXISTS.getMessage());
        }

        paymentEntity = businessMapper.convertToPaymentEntity(paymentDto);
        paymentRepository.save(paymentEntity);

        List<PaymentEntity> paymentEntities = orderEntity.getPaymentEntity();
        paymentEntities.add(paymentEntity);
        orderEntity.setPaymentEntity(paymentEntities);
        orderRepository.save(orderEntity);

        return businessMapper.convertToPaymentDto(paymentEntity);
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto, Long paymentId) {
        PaymentEntity paymentEntity = getPaymentById(paymentId);
        if (paymentEntity == null) {
            throw new PaymentIsNotExistException(ResultEnum.PAYMENT_NOT_EXISTS.getMessage());
        }

        if (paymentDto.getDate() != null && paymentDto.getTime() != null) {
            paymentEntity.setDateTime(LocalDateTime.of(paymentDto.getDate(), paymentDto.getTime()));
        }
        if (paymentDto.getAmount() != null) {
            paymentEntity.setAmount(paymentDto.getAmount());
        }
        if (paymentDto.getClientName() != null) {
            paymentEntity.setClientName(paymentDto.getClientName());
        }
        if (paymentDto.getAttachmentDto() != null) {
            paymentDto.setAttachmentDto(paymentDto.getAttachmentDto());
        }

        paymentRepository.save(paymentEntity);
        return businessMapper.convertToPaymentDto(paymentEntity);
    }

    @Override
    public void deletePayment(int orderId, Long paymentId) {
        OrderEntity orderEntity = orderRepository.findById(orderId);
        PaymentEntity paymentEntity = paymentRepository.getById(paymentId);
        if (orderEntity == null) {
            throw new OrderIsNotExistException(ResultEnum.ORDER_NOT_EXISTS.getMessage());
        }
        if (paymentEntity == null) {
            throw new PaymentIsNotExistException(ResultEnum.PAYMENT_NOT_EXISTS.getMessage());
        }

        orderEntity.getPaymentEntity().remove(paymentEntity);
        orderRepository.save(orderEntity);
    }


    @Override
    public List<PaymentDto> findPaymentsByOrderId(int id) {
        OrderEntity orderEntity = orderRepository.findById(id);
        if (orderEntity == null) {
            throw new OrderIsNotExistException(ResultEnum.ORDER_NOT_EXISTS.getMessage());
        }
        return businessMapper.collectionToList(orderEntity.getPaymentEntity(), businessMapper.paymentToDto);
    }

    @Override
    public PaymentDto findPaymentById(Long id) {
        return businessMapper.convertToPaymentDto(getPaymentById(id));
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return businessMapper.collectionToList(paymentRepository.findAll(), businessMapper.paymentToDto);
    }

    private PaymentEntity getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentIsNotExistException(ResultEnum.PAYMENT_NOT_EXISTS.getMessage()));
    }

    //========================payment===========================================


    //  attachment

    @Override
    public List<AttachmentDto> getAllAttachments() {

        List<AttachmentEntity> attachmentEntityList = attachmentRepository.findAll();

        if (attachmentEntityList.isEmpty()) throw new AttachmentIsNotExistException("Attachments are not exist");

        return businessMapper.collectionToList(attachmentEntityList, businessMapper.attachmentFromEntityToDto);

    }

    @Override
    public AttachmentDto getAttachmentById(int id) {

        AttachmentEntity attachmentEntity = attachmentRepository.findById(id)
                .orElseThrow(AttachmentIsNotExistException::new);

        return businessMapper.getAttachmentDto(attachmentEntity);

    }

    @Override
    public AttachmentDto createAttachment(AttachmentDto attachmentDto) {

        AttachmentEntity attachmentEntity = businessMapper.getAttachmentEntity(attachmentDto);

        if (attachmentRepository.findById(attachmentDto.getId()).isEmpty()) {
            attachmentRepository.save(attachmentEntity);
        } else {
            throw new AttachmentIsAlreadyExistException("Attachment is already exist");
        }

        return businessMapper.getAttachmentDto(attachmentEntity);
    }

    @Override
    public void deleteAttachment(int id) {

        AttachmentEntity attachmentEntity = attachmentRepository.findById(id)
                .orElseThrow(AttachmentIsNotExistException::new);

        attachmentRepository.delete(attachmentEntity);
    }

    @Override
    public AttachmentDto updateAttachment(AttachmentDto attachmentDto) {

        if (attachmentRepository.findById(attachmentDto.getId()).isEmpty()) {
            throw new AttachmentIsNotExistException("Attachment is not exist");
        }

        AttachmentEntity attachmentEntity = businessMapper.getAttachmentEntity(attachmentDto);

        attachmentEntity.setName(attachmentDto.getName());
        attachmentEntity.setCreated(attachmentDto.getCreated());
        attachmentEntity.setContent(attachmentDto.getContent());
        attachmentRepository.save(attachmentEntity);

        return businessMapper.getAttachmentDto(attachmentEntity);
    }


    //  discount

    @Override
    public List<DiscountDto> getAllDiscounts() {

        List<DiscountEntity> discountEntityList = discountRepository.findAll();

        if (discountEntityList.isEmpty()) throw new DiscountIsNotExistException("Discounts are not exist");

        return businessMapper.collectionToList(discountEntityList, businessMapper.discountFromEntityToDto);

    }

    @Override
    public DiscountDto createDiscount(DiscountDto discountDto) {
        DiscountEntity discountEntity = discountRepository.getById(discountDto.getId());

        if (discountEntity != null) {
            throw new RuntimeException("Discount is already exist");
        }

        discountEntity = businessMapper.getDiscountEntity(discountDto);
        discountRepository.save(discountEntity);
        return businessMapper.getDiscountDto(discountEntity);
    }

    @Override
    public void deleteDiscount(int id) {
        discountRepository.deleteById(id);
    }

    @Override
    public DiscountDto updateDiscount(DiscountDto discountDto) {

        if (discountRepository.findById(discountDto.getId()).isEmpty()) {
            throw new DiscountIsNotExistException("Discount doesn't exist");
        }

        DiscountEntity discountEntity = businessMapper.getDiscountEntity(discountDto);

        discountEntity.setPercent(discountDto.getPercent());
        discountEntity.setExpiration(discountDto.getExpiration());

        discountRepository.save(discountEntity);

        return businessMapper.getDiscountDto(discountEntity);

    }

    @Override
    public String checkProductQuantity(CartDto cartDto) {
        CartEntity cartEntity = businessMapper.getCartEntity(cartDto);
        Set<LineItemEntity> lineItemEntities = cartEntity.getLineItemEntitySet();

        for (LineItemEntity line : lineItemEntities) {
            if (line.getQuantity() > line.getProduct().getQuantity()) {
                return ResultEnum.BIG_QUANTITY.getMessage();
            } else {
                updateLineQuantity(line.getId(), line.getQuantity());
            }
        }
        return HttpStatus.OK.toString();
    }

    @Override
    public List<ProductDto> checkNotAvailableProducts(CartDto cartDto) {

        CartEntity cartEntity = businessMapper.getCartEntity(cartDto);
        System.out.println(cartEntity.toString());
        List<ProductEntity> productEntityList = new LinkedList<>();

        for (LineItemEntity lineItem : cartEntity.getLineItemEntitySet()) {

            ProductEntity productEntity = productRepository.findById(lineItem.getProduct().getId());

            if (lineItem.getQuantity() >= productEntity.getQuantity()) {
                productEntityList.add(lineItem.getProduct());
            }
        }
        cartRepository.save(cartEntity);
        return businessMapper.collectionToList(productEntityList, businessMapper.productToDto);
    }

    @Override
    public void deleteLineItemFromCartByProductId(int productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        CartEntity cartEntity = userEntity.getCartEntity();


        Set<LineItemEntity> lineItemEntity = cartEntity.getLineItemEntitySet();
        Set<LineItemEntity> deleteLines = new HashSet<>();
        for (LineItemEntity line : lineItemEntity) {
            if (line.getProduct().getId() == productId) {
//                deleteLineItemFromCart(line.getId());
//               LineItemEntity deletedLine = lineItemRepository.findById(line.getId()).orElseThrow();
                deleteLines.add(line);
//                cartEntity.getLineItemEntitySet().remove(deletedLine);
//                lineItemRepository.delete(deletedLine);
            }

        }

//        LineItemEntity deletedLine = lineItemRepository.findById(lineId).orElseThrow();
//        cartEntity.getLineItemEntitySet().remove(deletedLine);
//        lineItemRepository.delete(deletedLine);

        cartEntity.getLineItemEntitySet().removeAll(deleteLines);
        cartRepository.save(cartEntity);
        lineItemRepository.deleteAll(deleteLines);
    }

//    @Override
//    public WishListDto createWishList(WishListDto wishListDto) {
//
//        WishListEntity wishListEntity = businessMapper.getWishListEntity(wishListDto);
//
//        if(wishListRepository.findById(wishListDto.getId()).isEmpty()){
//            wishListRepository.save(wishListEntity);
//        }else{
//            throw new WishListIsAlreadyExistException("WishList is already exist");
//        }
//        return businessMapper.getWishListDto(wishListEntity);
//    }

    @Override
    public WishListDto createWishList(WishListDto wishListDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        WishListEntity wishListEntity = wishListRepository.findByTitle(wishListDto.getTitle());
        if (wishListEntity != null) {
            throw new WishListIsAlreadyExistException("WishList is already exist");
        }
        wishListEntity = businessMapper.getWishListEntity(wishListDto);
        wishListRepository.save(wishListEntity);

        userEntity.getWishListEntities().add(wishListEntity);
        userRepository.save(userEntity);

        return businessMapper.getWishListDto(wishListEntity);
    }


    @Override
    public WishListDto updateWishList(WishListDto wishListDto) {

        WishListEntity wishListEntity = wishListRepository.findById(wishListDto.getId())
                .orElseThrow(() -> new WishListIsNotExistException("Wish is not exist"));

        List<ProductEntity> productEntities = businessMapper.collectionToList(wishListDto.getProductDto(), businessMapper.productToEntity);
        if (!wishListDto.getTitle().isEmpty()) {
            wishListEntity.setTitle(wishListDto.getTitle());
        }
        if (wishListDto.getProductDto().size() != 0) {
            wishListEntity.setProductEntities(productEntities);
        }
        wishListRepository.save(wishListEntity);
//        if (wishListRepository.findById(wishListDto.getId()).isEmpty()) {
//            throw new WishListIsNotExistException("WishList is not exist");
//        }
//        WishListEntity wishListEntity = businessMapper.getWishListEntity(wishListDto);
//

//        wishListEntity.setTitle(wishListDto.getTitle());
//        wishListEntity.setProductEntities(
//                new LinkedList<ProductEntity>(
//                        productRepository.findAll(wishListDto.getProductDto()))
//        );

//        wishListRepository.save(wishListEntity);

        return businessMapper.getWishListDto(wishListEntity);
    }


    @Override
    public WishListDto deleteWishList(int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        WishListEntity wishListEntity = wishListRepository.findById(id)
                .orElseThrow(WishListIsNotExistException::new);

        userEntity.getWishListEntities().remove(wishListEntity);
        userRepository.save(userEntity);
        wishListRepository.delete(wishListEntity);
        return businessMapper.getWishListDto(wishListEntity);
    }


    @Override
    public WishListDto getWishListById(int id) {

        WishListEntity wishListEntity = wishListRepository.findById(id)
                .orElseThrow(WishListIsNotExistException::new);

        return businessMapper.getWishListDto(wishListEntity);
    }


    @Override
    public List<WishListDto> getAllWishLists() {

        List<WishListEntity> wishListEntities = wishListRepository.findAll();

        if (wishListEntities.isEmpty()) throw new WishListIsNotExistException("Wish lists are not exist");

        return businessMapper.collectionToList(wishListEntities, businessMapper.wishListFromEntityToDto);

    }


    @Override
    public List<WishListDto> getWishListsByUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        Set<WishListEntity> wishListEntities = userEntity.getWishListEntities();

        return businessMapper.collectionToList(wishListEntities, businessMapper.wishListFromEntityToDto);
    }


    @Override
    public WishListDto getWishListByTitle(String title) {
        if (wishListRepository.findByTitle(title) == null)
            throw new WishListIsNotExistException("Wish list is not found");
        else {
            WishListEntity wishListEntity = wishListRepository.findByTitle(title);
            return businessMapper.getWishListDto(wishListEntity);
        }
    }

    @Override
    public List<ProductDto> getProductsByWishId(int id) {

        WishListEntity wishListEntity = wishListRepository.findById(id)
                .orElseThrow(WishListIsNotExistException::new);

        List<ProductEntity> productEntityList = wishListEntity.getProductEntities();

        return businessMapper.collectionToList(productEntityList, businessMapper.productToDto);
    }

    @Override
    public WishListDto addProductToWishList(int productId, int wishListId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        ProductEntity productEntity = productRepository.findById(productId);

        WishListEntity wishListEntity = wishListRepository.findById(wishListId).orElseThrow(() -> new WishListIsNotExistException("Wish list not found"));
        List<ProductEntity> productEntities = new LinkedList<>();

        for (WishListEntity wish : userEntity.getWishListEntities()) {
            if (wish.getTitle().equals(wishListEntity.getTitle())) {
                productEntities.addAll(wish.getProductEntities());
                productEntities.add(productEntity);
                wish.setProductEntities(productEntities);
            }
        }
        wishListRepository.save(wishListEntity);
        userRepository.save(userEntity);
        return businessMapper.getWishListDto(wishListEntity);
    }

    @Override
    public WishListDto deleteAllProductsFromWishList(int wishId) {

        WishListEntity wishListEntity = wishListRepository.findById(wishId)
                .orElseThrow(WishListIsNotExistException::new);

        List<ProductEntity> productEntityList = wishListEntity.getProductEntities();
//        for(ProductEntity productEnt: wishListEntity.getProductEntities()){
//            productEntityList.remove(productEnt);
//        }
        wishListEntity.getProductEntities().removeAll(productEntityList);
        // wishListEntity.getProductEntities().clear();

        // wishListEntity.setProductEntities(productEntityList);
//        wishListEntity.getProductEntities().removeAll(productEntityList);
        wishListRepository.save(wishListEntity);
        return businessMapper.getWishListDto(wishListEntity);
    }

    @Override
    public WishListDto deleteProductFromWishList(int wishId, int productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        WishListEntity wishListEntity = wishListRepository.findById(wishId).orElseThrow(WishListIsNotExistException::new);
        ProductEntity deletedProduct = productRepository.findById(productId);

        wishListEntity.getProductEntities().remove(deletedProduct);
        wishListRepository.save(wishListEntity);


        userRepository.save(userEntity);

        return businessMapper.getWishListDto(wishListEntity);
    }

    @Override
    public WishListDto moveProductToWishList(int fromWishId, int toWishId, List<ProductDto> productDto) {
        WishListEntity fromWish = wishListRepository.findById(fromWishId).orElseThrow(WishListIsNotExistException::new);
        WishListEntity toWish = wishListRepository.findById(toWishId).orElseThrow(WishListIsNotExistException::new);

        List<ProductEntity> productEntities = businessMapper.collectionToList(productDto, businessMapper.productForListToEntity);
        for (ProductEntity p : productEntities) {
            fromWish.getProductEntities().remove(p);
        }
        wishListRepository.save(fromWish);

        toWish.getProductEntities().addAll(productEntities);
        wishListRepository.save(toWish);

        return businessMapper.getWishListDto(toWish);
    }

    @Override
    public WishListDto deleteProductsFromWishList(int wishId, List<ProductDto> productDtos) {

        WishListEntity wishListEntity = wishListRepository.findById(wishId)
                .orElseThrow(WishListIsNotExistException::new);

        List<ProductEntity> productEntityList = businessMapper.collectionToList(productDtos, businessMapper.productForListToEntity);

        for (ProductEntity p : productEntityList) {
            wishListEntity.getProductEntities().remove(p);
        }

        wishListRepository.save(wishListEntity);
        return businessMapper.getWishListDto(wishListEntity);
    }

    @Override
    public boolean checkIfProductInCart(int id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName());
        return userEntity.getCartEntity().getLineItemEntitySet().stream().filter(item -> item.getProduct().getId() == id).count() == 1;
    }

}
