package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.dto.formdto.AddProductDtoRequest;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.serviceinterface.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private BusinessMapper productMapper;

    @Resource
    private ProductService productService;


    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody AddProductDtoRequest addProductDtoRequest) {

        ProductDto productDto = productMapper.getProductDtoToAddProduct(addProductDtoRequest);

        return ResponseEntity.ok(productService.addProduct(productDto));

    }

    @GetMapping("/find-by-name/{name}")//Search product using search bar to show all available results
    public ResponseEntity<Set<ProductDto>> findByName(@PathVariable("name") String name){

        System.out.println(ResponseEntity.ok(productService.findProductByName(name)));
        return ResponseEntity.ok(productService.findProductByName(name));
    }


    @GetMapping("/find-by/{id}")//find and show product from product list using productId
    public ResponseEntity<ProductDto> findById(@PathVariable("id") int id) {

        return ResponseEntity.ok(productService.getProductById(id));

    }



    @GetMapping("/update/{id}")
    public ResponseEntity<ProductDto> getProductForUpdate(@PathVariable("id") int id) {

        return ResponseEntity.ok(productService.getProductById(id));

    }


    //TODO FIX Category changing bug
    @PostMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {

        System.out.println(productDto.toString());
        return ResponseEntity.ok(productService.updateProduct(productDto));

    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable("id") int id) {

        ProductDto productDto = productService.getProductById(id);

        productService.deleteProduct(productDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/show-all")
    public ResponseEntity<List<ProductDto>> showAllProducts() {

        return ResponseEntity.ok(productService.getAllProducts());

    }

}
