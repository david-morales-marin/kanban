package com.example.kanban.entitys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    //@Column(name = "status")
    //@OneToMany
    //private ProjectStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;


}
