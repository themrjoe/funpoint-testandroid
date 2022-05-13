package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> getEventByCategoryTitle(String title);

    List<Event> getAllByCategoryAndOnModerationIsFalse(Category category);

    List<Event> getAllByOnModerationIsTrueAndDeclinedIsFalseAndModeratingStatusIsNull();

    List<Event> getAllByOnModerationIsFalse();
}
