package com.example.demo.controller;

import com.example.demo.service.EventService;
import com.example.demo.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    public void addEvent(Event event) {
        event.setEvent_time(new Time(event.getTimeHelper().getTime()));
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
                            @RequestParam(value = "event_date") Date event_date,
                            @RequestParam(value = "event_time") Time event_time,
                            @RequestParam(value = "price") double price,
                            @RequestParam(value = "description") String description) {
        eventService.updateEvent(id, title, id_category, address, event_date, event_time, price, description);
    }
}
