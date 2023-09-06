package com.example.kanban.entitys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_Status")
    private ProjectStatus projectStatus;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;


}
