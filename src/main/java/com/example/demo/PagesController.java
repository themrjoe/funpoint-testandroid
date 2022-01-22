package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@Controller
public class PagesController {

    private EventController eventController;

    private CategoryController categoryController;

    private EventRepository eventRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setEventController(EventController eventController) {
        this.eventController = eventController;
    }

    /*index.html*/

    @PostMapping("/")
    public String addEventByIndex(@ModelAttribute Event event) {
        eventController.addEvent(event);
        return "redirect:/";
    }

    @GetMapping("/")
    public String goToMain(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    /*events*/
    /*allEvents.html*/

    @PostMapping("/allEvents")
    public String eventSubmit(@ModelAttribute Event event) {
        eventController.addEvent(event);
        return "redirect:/allEvents";
    }

    @GetMapping("/allEvents")
    public String goToAllEvents(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("events", eventRepository.findAll());
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
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventRepository.getEventByCategoryTitle("Еда"));
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
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventRepository.getEventByCategoryTitle("Музыка"));
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
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventRepository.getEventByCategoryTitle("Танцы"));
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
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventRepository.getEventByCategoryTitle("Спорт"));
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
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("events", eventRepository.getEventByCategoryTitle("Образование"));
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
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/android")
    @ResponseBody
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/android/food")
    @ResponseBody
    public List<Event> getAllCategories() {
        return eventRepository.getEventByCategoryTitle("Еда");
    }

    @GetMapping("/android/music")
    @ResponseBody
    public List<Event> getFoodEvents() {
        return eventRepository.getEventByCategoryTitle("Еда");
    }

    @GetMapping("/android/dance")
    @ResponseBody
    public List<Event> getDanceEvents() {
        return eventRepository.getEventByCategoryTitle("Танцы");
    }

    @GetMapping("/android/sport")
    @ResponseBody
    public List<Event> getSportEvents() {
        return eventRepository.getEventByCategoryTitle("Спорт");
    }

    @GetMapping("/android/education")
    @ResponseBody
    public List<Event> getEduEvents() {
        return eventRepository.getEventByCategoryTitle("Образование");
    }

    @GetMapping("/android/other")
    @ResponseBody
    public List<Event> getOtherEvents() {
        return eventRepository.getEventByCategoryTitle("Другое");
    }
}
