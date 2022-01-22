package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_event")
    private int id_event;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    @JsonIgnore
    private Category category;

    @Column(name = "address")
    private String address;

    @Column(name = "event_date")
    /*@DateTimeFormat(pattern = "dd-MM-yyyy")*/
    private Date event_date;

    @Column(name = "event_time")
    private Time event_time;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @DateTimeFormat(pattern = "HH:mm")
    private java.util.Date timeHelper;

    private String categoryTitle;
}

