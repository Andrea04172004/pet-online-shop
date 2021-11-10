package com.sazonov.mainonlineshop.serviceinterface;
import com.sazonov.mainonlineshop.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CategoryDto categoryDto);

    void deleteCategory(String name);

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(int id);

    CategoryDto getCategoryByName(String name);

}
