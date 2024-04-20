package com.tranv.d7shop.controller;

import com.tranv.d7shop.dtos.CategoryDTO;
import com.tranv.d7shop.models.Category;
import com.tranv.d7shop.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> saveCategory(
            @Valid @RequestBody CategoryDTO categoriesDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(categoriesDTO);
        return ResponseEntity.ok("Insert Category Successfully");
    }

    // Hiện tất cả các categories
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update Category Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category deleted " + id + " Successfully");
    }


}
