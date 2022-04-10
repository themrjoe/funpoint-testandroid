package com.example.demo.entity.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.SpringVersion;

@Data
@Builder
public class EventDto {

    private int id;
    private String title;
    private String address;
    private String eventDate;
    private String eventTime;
    private double price;
    private String phoneNumber;
    private String description;
    private String categoryTitle;
    private boolean favouriteForCurrentUser;
}
