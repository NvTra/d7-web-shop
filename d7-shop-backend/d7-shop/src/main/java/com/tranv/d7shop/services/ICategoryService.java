package com.tranv.d7shop.services;

import com.tranv.d7shop.dtos.CategoryDTO;
import com.tranv.d7shop.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(long id);

    Category updateCategory(long CategoryId, CategoryDTO categoryDTO);

    List<Category> getAllCategories();

    void deleteCategoryById(long id);
}
