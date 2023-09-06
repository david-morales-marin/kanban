package com.example.kanban.entitys;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_Status")
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_Type")
    private TaskType taskType;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;


}
