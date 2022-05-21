package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.ModerationCategoryDto;
import com.example.demo.entity.dto.ModerationDto;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.service.CategoryService;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://fun-point-app.herokuapp.com")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final EventService eventService;
    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/admin/users/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
        User result = userService.findById(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/admin/events")
    @ResponseBody
    public List<Event> getModerationEvents() {
        return eventService.getModerationEvents();
    }

    @PostMapping("/admin/moderate_event")
    @ResponseBody
    public Event moderateEvent(@RequestBody ModerationDto moderationDto, @RequestHeader("Authorization") String token) {
        return eventService.moderate(moderationDto, resolveUsernameByToken(token));
    }

    @GetMapping("/admin/categories")
    @ResponseBody
    public List<Category> getModerationCategories() {
        return categoryService.getModerationCategories();
    }

    @PostMapping("/admin/moderate_category")
    @ResponseBody
    public Category moderateCategory(@RequestHeader("Authorization") String token, @RequestBody ModerationCategoryDto dto) {
        return categoryService.moderate(dto, resolveUsernameByToken(token));
    }

    private String resolveUsernameByToken(String token) {
        return jwtTokenProvider.getUserName(jwtTokenProvider.resolveTokenFromHeader(token));
    }
}
