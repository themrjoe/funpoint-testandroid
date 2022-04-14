package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category getCategoryByTitle(String title);
    List<Category> getAllByOnModerationIsFalse();
    List<Category> getAllByOnModerationIsTrueAndDeclinedIsFalse();
}
