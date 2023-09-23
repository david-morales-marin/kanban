package com.example.kanban.controllers;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/* DELETE -> /v1/tasks/{id} // eliminar un Task
   GET -> /v1/tasks/{id} // obtener un Task */
@RestController()
@RequestMapping("/v1/tasks")
public class TaskController {

    @Autowired
    private TaskServices taskServices;


    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") UUID id){
        return this.taskServices.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") UUID id){
        this.taskServices.deleteTaskById(id);
    }



}
