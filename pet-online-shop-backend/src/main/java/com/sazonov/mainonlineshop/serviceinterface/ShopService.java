package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.*;

import java.util.List;

public interface ShopService {


    CartDto addProductToCart(int id, int quantity);

    CartDto getUserCart();

    LineItemDto updateLineQuantity(int lineId, int quantity);

    void deleteLineItemFromCart(int lineId);


    PaymentDto createPayment(PaymentDto paymentDto, int orderId);

    PaymentDto updatePayment(PaymentDto paymentDto, Long paymentId);

    void deletePayment(int orderId, Long id);

    PaymentDto findPaymentById(Long id);

    List<PaymentDto> getAllPayments();

    List<PaymentDto> findPaymentsByOrderId(int id);

    LineItemDto addLineItemToCart(int productId);

    LineItemDto deleteByLineFromCart(int lineId);

    LineItemDto deleteByProductFromCart(int productId);


//    OrderDto placeOrder();

//    CategoryDto saveCategory(CategoryDto categoryDto);
//
//    CategoryDto getCategory(String name);
//
//    List<CategoryDto> findAllCategories();


// Attachment

    List<AttachmentDto> getAllAttachments();

    AttachmentDto getAttachmentById(int id);

    AttachmentDto createAttachment(AttachmentDto attachmentDto);

    void deleteAttachment(int id);

    AttachmentDto updateAttachment(AttachmentDto attachmentDto);


    //  Discount

    List<DiscountDto> getAllDiscounts();

    DiscountDto createDiscount(DiscountDto discountDto);

    void deleteDiscount(int id);

    DiscountDto updateDiscount(DiscountDto discountDto);


    String checkProductQuantity(CartDto cartDto);

    List<ProductDto> checkNotAvailableProducts(CartDto cartDto);

    void deleteLineItemFromCartByProductId(int productId);

    //Wish List
    WishListDto createWishList(WishListDto wishListDto);

    WishListDto updateWishList(WishListDto wishListDto); //TODO nuzno li peredavat i id?

    WishListDto deleteWishList(int id);

    WishListDto getWishListById(int id);

    List<WishListDto> getAllWishLists();

    List<WishListDto> getWishListsByUser();

    WishListDto getWishListByTitle(String title);

    List<ProductDto> getProductsByWishId(int id);

    WishListDto addProductToWishList (int productId, int wishListId);

    WishListDto deleteAllProductsFromWishList(int wishId);

    WishListDto deleteProductFromWishList(int wishId, int productId);

    WishListDto moveProductToWishList (int fromWishId,int toWishId, List<ProductDto> productDto);

    WishListDto deleteProductsFromWishList(int wishId, List<ProductDto> productDtos);


    boolean checkIfProductInCart(int id);

}
