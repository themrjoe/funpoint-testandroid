package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_category")
    private int id_category;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Event> eventList;

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void addEvent(Event e) {
        List<Event> list = new ArrayList<>(this.getEventList());
        list.add(e);
        this.setEventList(list);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id_category=" + id_category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Category(int id_category, String title, String description) {
        this();
        this.setId_category(id_category);
        this.setDescription(description);
        this.setTitle(title);
        this.setEventList(new ArrayList<>());
    }
}



