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

    @Test
    public void shouldShowAllData_toUnauthorizedUser() {}

    @Test
    public void shouldRejectRegistration_invalidUsername() {}

    @Test
    public void shouldRejectRegistration_invalidPassword() {}

    @Test
    public void shouldRejectRegistration_userIsAlreadyExists() {}

    @Test
    public void shouldSuccessfullyRegisterUser() {}

    @Test
    public void shouldNotLogin_withoutPassword() {}

    @Test
    public void shouldNotLogin_withoutUsername() {}

    @Test
    public void shouldLogin_whenAllDataIsValid() {}

    @Test
    public void shouldSuccessfullyAddEvent_toModeration() {}

    @Test
    public void shouldNotAddEvent_withoutNeededFields() {}

    @Test
    public void shouldReturnAllEvents() {}

    @Test
    public void shouldReturnAllEvents_onModeration() {}

    @Test
    public void shouldChangeEventStatus_toRework() {}

    @Test
    public void shouldChangeEventStatus_toAccepted() {}

    @Test
    public void shouldChangeEventStatus_toDeclined() {}

    @Test
    public void shouldThrow403_whenGettingModerationEvents_withoutAccess() {}

    @Test
    public void shouldThrow403_whenTryingToAddEvent_withoutLogin() {}

    @Test
    public void shouldThrow403_whenTryingToAddEventToFavourites_withoutLogin () {}
}
