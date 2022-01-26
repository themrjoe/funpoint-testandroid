package com.example.demo;

import com.example.demo.entity.Category;
import com.example.demo.entity.Event;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldAddEvent_andCategory() {
        Category category = new Category();
        category.setTitle("Test");

        Event event = new Event();
        event.setTitle("Test");
        event.setCategory(category);
        category.setEventList(Collections.singletonList(event));
        categoryRepository.save(category);
        eventRepository.save(event);

        Assertions.assertNotNull(eventRepository.findAll());
        Assertions.assertEquals(event.getTitle(), eventRepository.getEventByTitle("Test").getTitle());
    }

}
