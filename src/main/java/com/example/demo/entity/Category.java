package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Event> eventList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User addCatByUser;

    private boolean onModeration = true;

    @Enumerated(EnumType.STRING)
    private ModeratingStatus moderatingStatus;

    private String moderatingMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moderating")
    @JsonIgnore
    private User moderatingBy;

    private boolean declined = false;

    @Override
    public String toString() {
        return "Category{" +
                "id_category=" + id_category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void addEvent(Event e) {
        List<Event> list = new ArrayList<>(this.getEventList());
        list.add(e);
        this.setEventList(list);
    }
}



