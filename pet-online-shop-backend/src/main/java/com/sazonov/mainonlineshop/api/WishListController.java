package com.sazonov.mainonlineshop.api;

import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.dto.WishListDto;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping ("/wish-list")
public class WishListController {

    @Resource
    private ShopService shopService;

    @PostMapping("/createWishList")
    public ResponseEntity<WishListDto> createWishList(@RequestBody WishListDto wishListDto){
        return ResponseEntity.ok(shopService.createWishList(wishListDto));
    }

    @PutMapping("/updateWishList")
    public ResponseEntity<WishListDto> updateWishList(@RequestBody WishListDto wishListDto){
        return ResponseEntity.ok(shopService.updateWishList(wishListDto));
    }

    @DeleteMapping("/deleteWishList/{id}")
    public ResponseEntity<WishListDto> deleteWisList(@PathVariable("id") int id){
        System.out.println("Wish id>"+id);

        return ResponseEntity.ok(shopService.deleteWishList(id));
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<WishListDto> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(shopService.getWishListById(id));
    }

    @GetMapping("/getAllWishLists")
    public ResponseEntity<List<WishListDto>> getAllWishLists(){
        return ResponseEntity.ok(shopService.getAllWishLists());
    }

    @GetMapping("/getAllWishListsByUser")
    public ResponseEntity<List<WishListDto>> getAllWishListsByUser(){
        return ResponseEntity.ok(shopService.getWishListsByUser());
    }

   @GetMapping("/getWishListByTitle/{title}")
    public ResponseEntity<WishListDto> getWishListByTitle(@PathVariable("title") String title){
        return ResponseEntity.ok(shopService.getWishListByTitle(title));
   }

   @GetMapping("/getProductsByWishId/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByWishId(@PathVariable("id") int id){
        return ResponseEntity.ok(shopService.getProductsByWishId(id));
   }


   @GetMapping ("/addToWishList/{productId}/{wishId}")
   public ResponseEntity<WishListDto> addProductToWishList(@PathVariable("productId") int productId,
                                           @PathVariable ("wishId") int wishListId) {
       System.out.println("Product id>"+productId);
      return ResponseEntity.ok(shopService.addProductToWishList(productId,wishListId));
    }

    @GetMapping("{wishId}/clear")
    public ResponseEntity<WishListDto> deleteAllProductsFromWishList(@PathVariable("wishId") String wishId){
        System.out.println("wish");
        System.out.println("Wish id>"+wishId);
        return ResponseEntity.ok(shopService.deleteAllProductsFromWishList(Integer.parseInt(wishId)));
    }


        @GetMapping("/deleteAllProductsFromWishList/{wishId}")
    public ResponseEntity<WishListDto> deleteAllProducts(@PathVariable("wishId") int wishId){
        System.out.println("Wish id>"+wishId);
        return ResponseEntity.ok(shopService.deleteAllProductsFromWishList(wishId));
    }

    @DeleteMapping ("/deleteProduct/{productId}/formWish/{wishId}")
    public ResponseEntity<WishListDto> deleteProductFromWishList(@PathVariable("wishId") int wishId,@PathVariable("productId") int productId) {
        return ResponseEntity.ok(shopService.deleteProductFromWishList(wishId, productId));
    }

    @PutMapping ("/moveProduct/{from}/{to}")
    public ResponseEntity<WishListDto> moveProductsToWishList (@PathVariable ("from") int fromWishId,@PathVariable ("to") int toWishId,
                                                              @RequestBody List<ProductDto> products){
        return ResponseEntity.ok(shopService.moveProductToWishList(fromWishId, toWishId, products));
    }

    @PutMapping("/deleteProducts/{wishId}")
    public ResponseEntity<WishListDto> deleteProductsFromWishList(@PathVariable("wishId") int wishId, @RequestBody List<ProductDto> productDtos){
        return ResponseEntity.ok(shopService.deleteProductsFromWishList(wishId, productDtos));
    }
}
