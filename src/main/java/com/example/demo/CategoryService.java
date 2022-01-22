package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(int id, String title, String description) {
        Category c = categoryRepository.getOne(id);
        c.setTitle(title);
        c.setDescription(description);
        categoryRepository.save(c);
    }
}
