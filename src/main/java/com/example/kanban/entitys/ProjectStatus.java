package com.example.kanban.entitys;

import jakarta.persistence.*;

import java.io.Serializable;

public enum ProjectStatus {
    ACTIVE,
    INACTIVE,
    PAUSED
}
