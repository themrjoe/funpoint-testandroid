package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    public void addCategory(Category category) {
        categoryService.addCategory(category);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
