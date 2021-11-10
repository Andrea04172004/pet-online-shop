package com.sazonov.mainonlineshop.serviceimplementation;


import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.repository.CategoryRepository;
import com.sazonov.mainonlineshop.repository.ProductRepository;
import com.sazonov.mainonlineshop.serviceinterface.ProductService;
import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private BusinessMapper productMapper;

    @Resource
    private CategoryRepository categoryRepository;


    @Override
    public ProductDto addProduct(ProductDto productDto) {

        ProductEntity productEntity = productMapper.getProductEntityToAddProduct(productDto);

        if (categoryRepository.findById(productEntity.getCategory().getId()).isEmpty()) {
            CategoryEntity ce = categoryRepository.save(productEntity.getCategory());
            productEntity.setCategory(ce);
        }
        productEntity = productRepository.save(productEntity);

        return productMapper.getProductDto(productEntity);
    }


    @Override
    public Set<ProductDto> findProductByName(String name) {

        return productMapper.collectionToSet(productRepository.findAllByNameContains(name), productMapper.productToDto);
    }



    @Override
    public ProductDto findProductByContainsName(String name) {
        return productMapper.getProductDto(productRepository.findOneByNameContains(name));
    }

    public ProductDto getProductById(int id) {

      return productMapper.getProductDto(productRepository.findById(id));

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {


        ProductEntity productEntity = productMapper.getProductEntityForUpdate(productDto);

        productRepository.save(productEntity);

        return productMapper.getProductDto(productEntity);

    }

    @Override
    public void deleteProduct(ProductDto productDto) {

        ProductEntity productEntity = productRepository.findById(productDto.getId());
        productRepository.delete(productEntity);

    }


    public List<ProductDto> getAllProducts() {

        List<ProductEntity> productEntityList = productRepository.findAll();

        return productMapper.collectionToList(productEntityList, productMapper.productToDto);
    }




}
