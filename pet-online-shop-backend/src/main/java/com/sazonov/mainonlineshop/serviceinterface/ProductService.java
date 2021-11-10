package com.sazonov.mainonlineshop.serviceinterface;


import com.sazonov.mainonlineshop.dto.ProductDto;


import java.util.*;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    Set<ProductDto> findProductByName(String name);

    ProductDto getProductById(int id);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto findProductByContainsName(String name);
}
