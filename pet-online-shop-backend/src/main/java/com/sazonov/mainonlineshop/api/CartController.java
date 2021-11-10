package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.CartDto;
import com.sazonov.mainonlineshop.dto.LineItemDto;
import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {



    @Resource
    private ShopService shopService;

    @GetMapping("/add-line-item/{id}")
    public ResponseEntity<LineItemDto> addLineItemToCart(@PathVariable("id") int productId){
        System.out.println("hi");
        System.out.println("productDto---> " + productId);
        return ResponseEntity.ok(shopService.addLineItemToCart(productId));
    }

    @DeleteMapping("/delete-by-line/{id}")
    public ResponseEntity<LineItemDto> deleteByLineFromCart(@PathVariable("id") int lineId){
        System.out.println("lineId---> " + lineId);
        return ResponseEntity.ok(shopService.deleteByLineFromCart(lineId));
    }

    @DeleteMapping("/delete-by-product/{id}")
    public ResponseEntity<LineItemDto> deleteByProductFromCart(@PathVariable("id") int productId) {
        System.out.println("productId - controller---> " + productId);
        return ResponseEntity.ok(shopService.deleteByProductFromCart(productId));
    }



    @PostMapping("/add/{id}")
    public ResponseEntity<CartDto> addToCart(@PathVariable("id") int id, @RequestBody int quantity) {
       return ResponseEntity.ok(shopService.addProductToCart(id, quantity));
    }

    @PutMapping ("/updateLine/{id}")
    public ResponseEntity<LineItemDto> updateLineQuantity(@PathVariable("id") int lineId , @RequestBody int quantity) {
        return ResponseEntity.ok(shopService.updateLineQuantity(lineId, quantity));
    }

    @GetMapping ("/getCart")
    public ResponseEntity<CartDto> getUserCart (){
        return ResponseEntity.ok(shopService.getUserCart());
    }

    @DeleteMapping ("/deleteLine/{id}")
    public void deleteLineItem (@PathVariable ("id") int id){
        System.out.println("controller ---> " + id);
        shopService.deleteLineItemFromCart(id);
    }

    @PostMapping("/getNotAvailableProducts")
    public ResponseEntity<List<ProductDto>> checkNotAvailableProducts(@RequestBody CartDto cartDto){
        return ResponseEntity.ok(shopService.checkNotAvailableProducts(cartDto));
    }

    @DeleteMapping("/deleteLineByProductId/{id}")
    public ResponseEntity<String> deleteLineItemFromCartByProductId(@PathVariable ("id") int id){
        System.out.println(id);
        shopService.deleteLineItemFromCartByProductId(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/contains/product/{id}")
    public ResponseEntity<Boolean> checkIfProductInCart(@PathVariable ("id") int id) {
        if (shopService.checkIfProductInCart(id)) {
            return ResponseEntity.ok(true);
        } else return ResponseEntity.ok(false);
    }

}
