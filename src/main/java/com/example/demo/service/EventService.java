package com.example.demo.service;

import com.example.demo.domain.RequestDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void saveEvent(Event event) {
        log.info(event.getCategoryTitle());
        Category c = categoryRepository.getCategoryByTitle(event.getCategoryTitle());
        event.setCategory(c);
        eventRepository.save(event);
        c.addEvent(event);
        categoryRepository.save(c);
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
