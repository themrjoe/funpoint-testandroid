package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    public void addEvent(Event event) {
        eventService.saveEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    public List<Event> getEventsByCategoryTitle(String title) {
        return eventService.getEventByCategoryTitle(title);
    }

    @DeleteMapping("/event")
    public void deleteEvent(@RequestParam("id") int id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("/event/update")
    public void updateEvent(@RequestParam(value = "id_event") int id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "id_category") int id_category,
                            @RequestParam(value = "address") String address,
                            @RequestParam(value = "event_date") String event_date,
                            @RequestParam(value = "event_time") String event_time,
                            @RequestParam(value = "price") double price,
                            @RequestParam(value = "description") String description) {
        eventService.updateEvent(id, title, id_category, address, event_date, event_time, price, description);
    }
}
