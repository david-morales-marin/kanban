package com.example.kanban.entitys;

import jakarta.persistence.*;

import java.io.Serializable;

public enum ProjectStatus {
    ACTIVE,
    INACTIVE,
    PAUSED
}

/*
@Entity
@Table(name = "ProjectStatus")
public class ProjectStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "active")
    private String active;

    @Column(name = "inactive")
    private String inactive;

    @Column(name = "paused")
    private String paused;

} */
