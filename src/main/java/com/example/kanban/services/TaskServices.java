package com.example.kanban.services;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.repositorys.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;

    public Optional<Task> getTaskById(UUID id){
        return this.taskRepository.findById(id);

    }

    public void deleteTaskById(UUID id){
        this.taskRepository.deleteById(id);
    }

    public Task createTask(Task task){
        return this.taskRepository.save(task);
    }

    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }




}
