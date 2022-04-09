package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategoryByUser(Category category, String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.warn("User with username: {} not found", username);
            return;
        }
        category.setAddCatByUser(user);
        if (CollectionUtils.isEmpty(user.getAddedCategories())) {
            List<Category> addedCategories = new ArrayList<>();
            addedCategories.add(category);
            user.setAddedCategories(addedCategories);
            categoryRepository.save(category);
            userRepository.save(user);
        }
        List<Category> addedCategories = user.getAddedCategories();
        addedCategories.add(category);
        user.setAddedCategories(addedCategories);
        categoryRepository.save(category);
        userRepository.save(user);
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
