package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

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
        Category c = categoryRepository.getById(id);
        c.setTitle(title);
        c.setDescription(description);
        categoryRepository.save(c);
    }
}
