package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @LastModifiedDate
    private Date updated;
    @CreatedDate
    private Date created;
    @Enumerated(EnumType.STRING)
    private Status status;
}
