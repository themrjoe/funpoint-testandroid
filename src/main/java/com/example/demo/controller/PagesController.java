package com.example.demo.controller;

import com.example.demo.domain.RequestDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import com.example.demo.entity.dto.EventDto;
import com.example.demo.entity.dto.FavouriteDto;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequiredArgsConstructor
public class PagesController {

    private final EventController eventController;
    private final CategoryController categoryController;
    private final EventService eventService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String FOOD = "Еда";
    private static final String MUSIC = "Музыка";
    private static final String DANCE = "Танцы";
    private static final String SPORT = "Спорт";
    private static final String EDU = "Образование";
    private static final String OTHER = "Другое";

    @PostMapping("/")
    public String addEventByIndex(@ModelAttribute Event event) {
        eventController.addEvent(event);
        return "redirect:/";
    }

    @GetMapping("/")
    public String goToMain(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        return "index";
    }

    @PostMapping("/allEvents")
    public String eventSubmit(@ModelAttribute Event event) {
        eventController.addEvent(event);
        return "redirect:/allEvents";
    }

    @GetMapping("/allEvents")
    public String goToAllEvents(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        model.addAttribute("events", eventController.getAllEvents());
        return "allEvents";
    }

    @PostMapping("/food")
    public String addEventInFood(@ModelAttribute Event event, @ModelAttribute Category category) {

        if (event.getAddress() == null) {
            categoryController.addCategory(category);
        } else {
            eventController.addEvent(event);
        }
        return "redirect:/food";
    }

    @GetMapping("/food")
    public String goToFood(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventController.getEventsByCategoryTitle(FOOD));
        return "food_ct";
    }

    @PostMapping("/music")
    public String addEventInMusic(@ModelAttribute Event event, @ModelAttribute Category category) {

        if (event.getAddress() == null) {
            categoryController.addCategory(category);
        } else {
            eventController.addEvent(event);
        }
        return "redirect:/music";
    }

    @GetMapping("/music")
    public String goToMusic(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventController.getEventsByCategoryTitle(MUSIC));
        return "music_ct";
    }

    @PostMapping("/dance")
    public String addEventInDance(@ModelAttribute Event event, @ModelAttribute Category category) {

        if (event.getAddress() == null) {
            categoryController.addCategory(category);
        } else {
            eventController.addEvent(event);
        }
        return "redirect:/dance";
    }

    @GetMapping("/dance")
    public String goToDance(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventController.getEventsByCategoryTitle(DANCE));
        return "dance_ct";
    }

    @PostMapping("/sport")
    public String addEventInSport(@ModelAttribute Event event, @ModelAttribute Category category) {

        if (event.getAddress() == null) {
            categoryController.addCategory(category);
        } else {
            eventController.addEvent(event);
        }
        return "redirect:/sport";
    }

    @GetMapping("/sport")
    public String goToSport(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventController.getEventsByCategoryTitle(SPORT));
        return "sport_ct";
    }

    @PostMapping("/education")
    public String addEventInEducation(@ModelAttribute Event event, @ModelAttribute Category category) {

        if (event.getAddress() == null) {
            categoryController.addCategory(category);
        } else {
            eventController.addEvent(event);
        }
        return "redirect:/education";
    }

    @GetMapping("/education")
    public String goToEducation(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventController.getEventsByCategoryTitle(EDU));
        return "education_ct";
    }

    @GetMapping("/other")
    public String goToOther() {
        return "other";
    }

    @PostMapping("/categories")
    public String addCategoryOrEvent(@ModelAttribute Category category, @ModelAttribute Event event) {

        if (event.getAddress() == null) {
            categoryController.addCategory(category);
        } else {
            eventController.addEvent(event);
        }
        return "redirect:/categories";
    }

    @GetMapping("/categories")
    public String goToCategories(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryController.getAllCategories());
        return "categories";
    }

    @GetMapping("/android")
    @ResponseBody
    public List<Event> getAllEvents() {
        return eventController.getAllEvents();
    }

    @GetMapping("/android/food")
    @ResponseBody
    public List<Event> getFoodEvents() {
        return eventController.getEventsByCategoryTitle(FOOD);
    }

    @GetMapping("/android/music")
    @ResponseBody
    public List<Event> geMusicEvents() {
        return eventController.getEventsByCategoryTitle(MUSIC);
    }

    @GetMapping("/android/dance")
    @ResponseBody
    public List<Event> getDanceEvents() {
        return eventController.getEventsByCategoryTitle(DANCE);
    }

    @GetMapping("/android/sport")
    @ResponseBody
    public List<Event> getSportEvents() {
        return eventController.getEventsByCategoryTitle(SPORT);
    }

    @GetMapping("/android/education")
    @ResponseBody
    public List<Event> getEduEvents() {
        return eventController.getEventsByCategoryTitle(EDU);
    }

    @GetMapping("/android/other")
    @ResponseBody
    public List<Event> getOtherEvents() {
        return eventController.getEventsByCategoryTitle(OTHER);
    }

    @GetMapping("/android/categories")
    @ResponseBody
    public List<Category> getAll() {
        return categoryController.getAllCategories();
    }

    @PostMapping("/android/category")
    @ResponseBody
    public List<Event> getEvents(@RequestBody RequestDto dto) {
        return eventService.getEventsByCategory(dto);
    }

    @GetMapping("/android/event/{id}")
    @ResponseBody
    public EventDto getEventById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (StringUtils.isBlank(token)) {
            return eventService.getEventById(id, "");
        }
        return eventService.getEventById(id, resolveUsernameByToken(token));
    }

    @PostMapping(value = "/add/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addEvent(@RequestBody Event event, @RequestHeader("Authorization") String token) {
        eventService.addEventByUser(event, resolveUsernameByToken(token));
    }

    @PostMapping(value = "/add/category", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addCategory(@RequestBody Category category, @RequestHeader("Authorization") String token) {
        categoryController.addCategoryByUser(category, resolveUsernameByToken(token));
    }

    @PostMapping(value = "/user/add_to_favourite", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EventDto addEventToFavourite(@RequestHeader("Authorization") String token, @RequestBody FavouriteDto dto) {
        return userService.addEventToFavourite(dto, resolveUsernameByToken(token));
    }

    @GetMapping(value = "/user/get_favourite")
    @ResponseBody
    public List<Event> getFavouriteEvents(@RequestHeader("Authorization") String token) {
        return userService.getFavouriteEventsByUserName(resolveUsernameByToken(token));
    }

    @GetMapping(value = "/user/get_added_events")
    @ResponseBody
    public List<Event> getAddedByUser(@RequestHeader("Authorization") String token) {
        return userService.getAddedEventsByUser(resolveUsernameByToken(token));
    }

    @GetMapping(value = "/user/get_added_categories")
    @ResponseBody
    public List<Category> getAddedCategoryByUser(@RequestHeader("Authorization") String token) {
        return userService.getAddedCategories(resolveUsernameByToken(token));
    }

    @DeleteMapping(value = "/user/delete_from_fav", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EventDto deleteFavouriteEvent(@RequestHeader("Authorization") String token, @RequestBody FavouriteDto dto) {
        return userService.deleteEventFromUserFavourites(dto.getIdEvent(), resolveUsernameByToken(token));
    }

    private String resolveUsernameByToken(String token) {
        return jwtTokenProvider.getUserName(jwtTokenProvider.resolveTokenFromHeader(token));
    }
}
