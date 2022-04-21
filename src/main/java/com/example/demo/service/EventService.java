package com.example.demo.service;

import com.example.demo.domain.RequestDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import com.example.demo.entity.ModeratingStatus;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.EventDto;
import com.example.demo.entity.dto.ModerationDto;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

    public EventDto getEventById(int id, String username) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return null;
        }
        boolean favouriteForCurrentUser;
        if (StringUtils.isBlank(username)) {
            favouriteForCurrentUser = false;
        } else {
            User user = userRepository.findByUserName(username);
            if (user == null) {
                favouriteForCurrentUser = false;
            } else {
                favouriteForCurrentUser = user.getEvents().contains(event);
            }
        }
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .address(event.getAddress())
                .eventDate(event.getEventDate())
                .eventTime(event.getEventTime())
                .price(event.getPrice())
                .phoneNumber(event.getPhoneNumber())
                .description(event.getDescription())
                .categoryTitle(event.getCategoryTitle())
                .favouriteForCurrentUser(favouriteForCurrentUser)
                .build();
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
        return eventRepository.getAllByOnModerationIsFalse();
    }

    public List<Event> getEventsByCategory(RequestDto requestDto) {
        return eventRepository.getAllByCategoryAndOnModerationIsFalse(categoryRepository.getById(requestDto.getCategoryId()));
    }

    public Event getEvent(int id) {
        return eventRepository.findById(id).orElse(null);
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

    public List<Event> getModerationEvents() {
        return eventRepository.getAllByOnModerationIsTrueAndDeclinedIsFalseAndModeratingStatusIsNull();
    }

    public Event moderate(ModerationDto moderationDto, String username) {
        User user = userRepository.findByUserName(username);
        Event event = eventRepository.findById(moderationDto.getEventId()).orElse(null);
        if (event == null) {
            return null;
        }
        switch (moderationDto.getStatus()) {
            case "ACCEPTED": {
                event.setOnModeration(false);
                event.setModeratingStatus(ModeratingStatus.ACCEPTED);
                event.setModeratingBy(user);
                event.setModeratingMessage("Событие успешно прошло модерацию. " +
                        "Можете насладиться Вашим высером сполна и позвать Ваших друзей чтобы поесть оборнейшего дерьма! " +
                        "С огромным неуважением, Администрация!" +
                        "Идите нахуй! :)");
                return enrichEventUser(event, user);
            }
            case "DECLINED": {
                event.setOnModeration(true);
                event.setDeclined(true);
                event.setModeratingBy(user);
                event.setModeratingMessage(moderationDto.getMessage());
                event.setModeratingStatus(ModeratingStatus.DECLINED);
                return enrichEventUser(event, user);
            }
            case "REWORK": {
                event.setOnModeration(true);
                event.setModeratingStatus(ModeratingStatus.REWORK);
                event.setModeratingMessage(moderationDto.getMessage());
                event.setModeratingBy(user);
                return enrichEventUser(event, user);
            }
            default:
                return null;
        }
    }

    public void reworkEvent(Event reworkedEvent) {
        Event event = eventRepository.findById(reworkedEvent.getId()).orElse(null);
        if (event == null) {
            return;
        }
        List<Event> eventList = event.getCategory().getEventList();
        eventList.remove(event);
        event.setCategory(null);
        event.setOnModeration(true);
        event.setModeratingMessage(null);
        event.setModeratingStatus(null);
        event.setDeclined(false);
        event.setTitle(reworkedEvent.getTitle());
        event.setAddress(reworkedEvent.getAddress());
        event.setEventDate(reworkedEvent.getEventDate());
        event.setEventTime(reworkedEvent.getEventTime());
        event.setPrice(reworkedEvent.getPrice());
        event.setPhoneNumber(reworkedEvent.getPhoneNumber());
        event.setDescription(reworkedEvent.getDescription());
        event.setCategoryTitle(reworkedEvent.getCategoryTitle());
        saveEvent(event);
    }

    private Event enrichEventUser(Event event, User user) {
        if (CollectionUtils.isEmpty(user.getModeratedEvents())) {
            List<Event> moderatedEvents = new ArrayList<>();
            moderatedEvents.add(event);
            user.setModeratedEvents(moderatedEvents);
            eventRepository.save(event);
            userRepository.save(user);
            return event;
        }
        List<Event> moderatedEvents = user.getModeratedEvents();
        moderatedEvents.add(event);
        user.setModeratedEvents(moderatedEvents);
        eventRepository.save(event);
        userRepository.save(user);
        return eventRepository.findById(event.getId()).orElse(null);
    }
}
