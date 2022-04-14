package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import com.example.demo.entity.ModeratingStatus;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.ModerationCategoryDto;
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

    public List<Category> getModerationCategories() {
        return categoryRepository.getAllByOnModerationIsTrueAndDeclinedIsFalse();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllByOnModerationIsFalse();
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

    public Category moderate(ModerationCategoryDto dto, String username) {
        User user = userRepository.findByUserName(username);
        Category category = categoryRepository.findById(dto.getIdCategory()).orElse(null);
        if (category == null) {
            return null;
        }
        switch (dto.getStatus()) {
            case "ACCEPTED": {
                category.setOnModeration(false);
                category.setModeratingStatus(ModeratingStatus.ACCEPTED);
                category.setModeratingBy(user);
                category.setModeratingMessage("Событие успешно прошло модерацию. " +
                        "Можете насладиться Вашим высером сполна и позвать Ваших друзей чтобы поесть оборнейшего дерьма! " +
                        "С огромным неуважением, Администрация!" +
                        "Идите нахуй! :)");
                return enrichEventUser(category, user);
            }
            case "DECLINED": {
                category.setOnModeration(true);
                category.setDeclined(true);
                category.setModeratingBy(user);
                category.setModeratingMessage(dto.getMessage());
                category.setModeratingStatus(ModeratingStatus.DECLINED);
                return enrichEventUser(category, user);
            }
            case "REWORK": {
                category.setOnModeration(true);
                category.setModeratingStatus(ModeratingStatus.REWORK);
                category.setModeratingMessage(dto.getMessage());
                category.setModeratingBy(user);
                return enrichEventUser(category, user);
            }
            default:
                return null;
        }
    }

    private Category enrichEventUser(Category category, User user) {
        if (CollectionUtils.isEmpty(user.getModeratedCategories())) {
            List<Category> moderatedCategories = new ArrayList<>();
            moderatedCategories.add(category);
            user.setModeratedCategories(moderatedCategories);
            categoryRepository.save(category);
            userRepository.save(user);
            return category;
        }
        List<Category> moderatedCategories = user.getModeratedCategories();
        moderatedCategories.add(category);
        user.setModeratedCategories(moderatedCategories);
        categoryRepository.save(category);
        userRepository.save(user);
        return categoryRepository.findById(category.getId_category()).orElse(null);
    }
}
