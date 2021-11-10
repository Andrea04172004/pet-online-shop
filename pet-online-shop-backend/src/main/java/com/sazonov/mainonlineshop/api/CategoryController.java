package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.CategoryDto;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.serviceinterface.CategoryService;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {


    @Resource
    private BusinessMapper businessMapper;

    @Resource
    private ShopService shopService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto> addCat(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CategoryDto> deleteCategory(@RequestParam String name){
        categoryService.deleteCategory(name);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

    @GetMapping("/getById")
    public ResponseEntity<CategoryDto> getCategoryById(@RequestParam int id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/getByName")
    public ResponseEntity<CategoryDto> getCategoryByName(@RequestParam String name){
        return  ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    /*------------------------------------------------------------------------*/


//    @PostMapping("/add")
//    public ResponseEntity<CategoryDto> addCategory(@RequestBody AddCategoryDtoRequest addCategoryDtoRequest) {
//
//        CategoryDto categoryDto = shopMapper.getCategoryDtoToAddCategory(addCategoryDtoRequest);
//
//        return ResponseEntity.ok(shopService.saveCategory(categoryDto));
//
//    }
//
//    @GetMapping("/find/{name}")
//    public ResponseEntity<CategoryDto> getCategoryInfo(@PathVariable("name") String name) {
//
//       return ResponseEntity.ok(shopService.getCategory(name));
//
//
//    }
//
//    @GetMapping ("/findAllCategories")
//    public ResponseEntity<List<CategoryDto>> findAllCategories (){
//        return ResponseEntity.ok(shopService.findAllCategories());
//    }

}
