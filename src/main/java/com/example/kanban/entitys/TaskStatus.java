package com.example.kanban.entitys;


public enum TaskStatus {
    TODO,
    INPROGRESS,
    BLOCKED,
    DONE;

    public static TaskStatus fromString(String status) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.name().equalsIgnoreCase(status)) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException("Estado de tarea no válido: " + status);
    }

}
