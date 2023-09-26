package com.example.kanban.models;

import com.example.kanban.entitys.Task;

import java.util.List;

public class BoardResponse {

    private String status;
    private List<Task> tasks;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
