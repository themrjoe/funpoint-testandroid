package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.dto.EventDto;
import com.example.demo.entity.dto.FavouriteDto;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EventService eventService;

    public User register(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            return null;
        }
        Role role = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("User with id: {} deleted", id);
    }

    public EventDto addEventToFavourite(FavouriteDto dto, String userName) {
        User user = userRepository.findByUserName(userName);
        Event event = eventRepository.findById(dto.getIdEvent()).orElse(null);
        if (user == null || event == null) {
            log.warn("Username or event id not exists: user id: {}, event id: {}", userName, dto.getIdEvent());
            return null;
        }
        if (CollectionUtils.isEmpty(user.getEvents())) {
            List<Event> events = new ArrayList<>();
            events.add(event);
            user.setEvents(events);
            userRepository.save(user);
            return eventService.getEventById(dto.getIdEvent(), userName);
        }
        List<Event> userEvents = user.getEvents();
        if (userEvents.contains(event)) {
            return null;
        }
        userEvents.add(event);
        user.setEvents(userEvents);
        userRepository.save(user);
        return eventService.getEventById(dto.getIdEvent(), userName);
    }

    public List<Event> getAddedEventsByUser(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.warn("User with username: {} not found", username);
            return Collections.emptyList();
        }
        return user.getAddedEvents();
    }

    public List<Event> getFavouriteEventsByUserName(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.warn("Username not exists: {}", username);
            return Collections.emptyList();
        }
        return user.getEvents();
    }

    public List<Category> getAddedCategories(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.warn("Username not exists: {}", username);
            return Collections.emptyList();
        }
        return user.getAddedCategories();
    }

    public EventDto deleteEventFromUserFavourites(int id, String username) {
        User user = userRepository.findByUserName(username);
        Event event = eventRepository.findById(id).orElse(null);
        if (user == null || event == null || CollectionUtils.isEmpty(user.getEvents())) {
            return null;
        }
        List<Event> favEvents = user.getEvents();
        favEvents.remove(event);
        user.setEvents(favEvents);
        userRepository.save(user);
        return eventService.getEventById(id, username);
    }
}
