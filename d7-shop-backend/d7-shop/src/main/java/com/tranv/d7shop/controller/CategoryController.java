package com.tranv.d7shop.controller;

import com.tranv.d7shop.components.LocalizationUtils;
import com.tranv.d7shop.dtos.CategoryDTO;
import com.tranv.d7shop.models.Category;
import com.tranv.d7shop.reponse.UpdateCategoryRepose;
import com.tranv.d7shop.services.ICategoryService;
import com.tranv.d7shop.utils.MessageKeys;
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
    private final LocalizationUtils localizationUtils;

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
    public ResponseEntity<UpdateCategoryRepose> updateCategory(
            @PathVariable long id,
            @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);

        return ResponseEntity.ok(UpdateCategoryRepose
                .builder()
                .message(localizationUtils.getLocalizedMessage(
                        MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category deleted " + id + " Successfully");
    }


}
