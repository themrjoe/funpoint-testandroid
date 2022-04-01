package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_event")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    @JsonIgnore
    private Category category;

    @Column(name = "address")
    private String address;

    @Column(name = "event_date")

    private String eventDate;

    @Column(name = "event_time")
    private String eventTime;

    @Column(name = "price")
    private double price;

    private String phoneNumber;

    @Column(name = "description")
    private String description;

    private String categoryTitle;
}

