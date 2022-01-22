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

    public void setTimeHelper(java.util.Date timeHelper) {
        this.timeHelper = timeHelper;
    }

    public java.util.Date getTimeHelper() {
        return timeHelper;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public java.util.Date getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Date event_date) {
        this.event_date = event_date;
    }

    public java.util.Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Time event_time) {
        this.event_time = event_time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}

