package com.sazonov.mainonlineshop.serviceimplementation;
import com.sazonov.mainonlineshop.dto.CategoryDto;
import com.sazonov.mainonlineshop.exception.CategoryIsAlreadyExistException;
import com.sazonov.mainonlineshop.exception.CategoryIsNotExistException;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.repository.CategoryRepository;
import com.sazonov.mainonlineshop.repository.ProductRepository;
import com.sazonov.mainonlineshop.serviceinterface.CategoryService;
import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryRepository categoryRepository;

    @Resource
    BusinessMapper businessMapper;

    @Resource
    ProductRepository productRepository;

    @Override
    public List<CategoryDto> getAllCategories() {

        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        if(categoryEntityList.isEmpty()) throw new CategoryIsNotExistException("Categories are not exist");

       return businessMapper.collectionToList(categoryEntityList, businessMapper.categoryToDto);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        CategoryEntity categoryEntity = businessMapper.getCategoryEntity(categoryDto);

       if(categoryRepository.findById(categoryDto.getId()).isEmpty()) {
           categoryRepository.save(categoryEntity);
       }else{
           throw new CategoryIsAlreadyExistException("Category is already exist");
       }

        return businessMapper.getCategoryDto(categoryEntity);
    }

    @Transactional
    @Override
    public void deleteCategory(String name) {
        CategoryEntity categoryEntity = categoryRepository.findByName(name);
            if(categoryEntity == null) {
                throw new CategoryIsNotExistException("Category is not found");
            }else{
                if(!(categoryEntity.getProductSet().isEmpty())){
                    CategoryEntity categoryEntityGeneral = categoryRepository.findByName("GeneralCategory");
                    categoryEntityGeneral.getProductSet().addAll(categoryEntity.getProductSet());
                    for(ProductEntity prod : categoryEntity.getProductSet()){
                        prod.setCategory(categoryEntityGeneral);
                    }
                    categoryEntity.getProductSet().clear();
                    categoryRepository.save(categoryEntity);
                    categoryRepository.save(categoryEntityGeneral);
                }
            categoryRepository.deleteByName(name);
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        CategoryEntity categoryEntity = businessMapper.getCategoryEntity(categoryDto);

        if(categoryRepository.findById(categoryDto.getId()).isEmpty()){
            throw new CategoryIsNotExistException("Category is not found");
        }else{
            categoryEntity.setName(categoryDto.getName());
            categoryEntity.setProductSet(new HashSet<>(productRepository.findAllByCategoryName(categoryDto.getName())));
            //categoryEntity.setParentCategory(productMapper.getCategoryEntity(categoryDto.getParentCategory()));
            categoryRepository.save(categoryEntity);
        }
            return businessMapper.getCategoryDto(categoryEntity);
    }


    @Override
    public CategoryDto getCategoryById(int id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(CategoryIsNotExistException :: new);
        return businessMapper.getCategoryDto(categoryEntity);
    }

    @Override
    public CategoryDto getCategoryByName(String name){
        if(categoryRepository.findByName(name) == null) throw  new CategoryIsNotExistException("Category is not found");
         else{
            CategoryEntity categoryEntity = categoryRepository.findByName(name);
            return businessMapper.getCategoryDto(categoryEntity);
        }
    }

}