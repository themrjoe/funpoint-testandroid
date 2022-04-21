package com.example.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends BaseEntity {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_events",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private List<Event> events;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "addByUser")
    private List<Event> addedEvents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "addCatByUser")
    private List<Category> addedCategories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moderatingBy")
    private List<Event> moderatedEvents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moderatingBy")
    private List<Category> moderatedCategories;

}

