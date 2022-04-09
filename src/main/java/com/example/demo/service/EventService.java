package com.example.demo.service;

import com.example.demo.domain.RequestDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.EventRepository;
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
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void saveEvent(Event event) {
        Category c = categoryRepository.getCategoryByTitle(event.getCategoryTitle());
        event.setCategory(c);
        eventRepository.save(event);
        c.addEvent(event);
        categoryRepository.save(c);
    }

    public void addEventByUser(Event event, String username) {
        Category c = categoryRepository.getCategoryByTitle(event.getCategoryTitle());
        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.warn("User with username: {} is not exists", username);
            return;
        }
        if (CollectionUtils.isEmpty(user.getAddedEvents())) {
            List<Event> addedEvents = new ArrayList<>();
            addedEvents.add(event);
            event.setAddByUser(user);
            event.setCategory(c);
            user.setAddedEvents(addedEvents);
            eventRepository.save(event);
            categoryRepository.save(c);
            userRepository.save(user);
        }
        List<Event> addedEvents = user.getAddedEvents();
        addedEvents.add(event);
        event.setAddByUser(user);
        event.setCategory(c);
        user.setAddedEvents(addedEvents);
        eventRepository.save(event);
        categoryRepository.save(c);
        userRepository.save(user);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByCategory(RequestDto requestDto) {
        return eventRepository.getAllByCategory(categoryRepository.getById(requestDto.getCategoryId()));
    }

    public List<Event> getEventByCategoryTitle(String title) {
        return eventRepository.getEventByCategoryTitle(title);
    }

    public void deleteEvent(int id) {
        eventRepository.deleteById(id);
    }

    public void updateEvent(int id, String title, int category_id, String address, String event_date, String event_time, double price, String description) {
        Event e = eventRepository.getById(id);
        e.setAddress(address);
        e.setDescription(description);
        e.setEventDate(event_date);
        e.setEventTime(event_time);
        e.setPrice(price);
        e.setTitle(title);
        Category c = categoryRepository.getById(category_id);
        e.setCategory(c);
        eventRepository.save(e);
    }
}
