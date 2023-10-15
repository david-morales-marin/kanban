package com.example.kanban.controllers;

import com.example.kanban.entitys.Task;
import com.example.kanban.entitys.TaskStatus;
import com.example.kanban.services.TaskServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
@RestController()
@RequestMapping("/v1")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Task controller", description = "Los metodos para la creación de las tareas relacionadas a un projecto")
public class TaskController {

    @Autowired
    private TaskServices taskServices;


    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") UUID id){
        return this.taskServices.getTaskById(id);
    }

    @GetMapping("/listaTask")
    public List<Task> getAllTask(){
        return this.taskServices.getAllTask();
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") UUID id){
        this.taskServices.deleteTaskById(id);
    }

    //GET /v1/projects/{id}/due-task
    @GetMapping("/projects/{project_id}/due-task")
    public ResponseEntity<List<Task>> getAllTasksexpired(@PathVariable UUID project_id) throws ChangeSetPersister.NotFoundException {

        List<Task> tasks = this.taskServices.getAllTaskexpired(project_id);
        LocalDateTime dateToday = LocalDateTime.now();
        List<Task> expiredTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (dateToday.isAfter(task.getDueDate())) {
                expiredTasks.add(task);
            }
        }

        if (!expiredTasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(expiredTasks);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PostMapping("/projects/{project_id}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable UUID project_id, @RequestBody Task task) throws
            ChangeSetPersister.NotFoundException {

             if(task.getName() == null || task.getName().isEmpty() ||
               task.getTaskType() == null ||
               task.getDescription() == null || task.getDescription().isEmpty()){

            /* puedo mejorar esto, creando una clase donde tenga el manejo de excepciones
            y mande un "mensaje" que diga el porque no esta creando nada
            asi como esta, no revienta pero sale en blanco la respuesta
            mirar las clases del profe juan y lugel sobre manejo de excepciones */

            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }else{

        task.setTaskStatus(TaskStatus.TODO);
        Task createdTask =  taskServices.createTaskk(project_id, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
         }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> updateRequest) {

        Task currentTask = taskServices.getTaskById(id).orElse(null);
        if (currentTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String statusString = updateRequest.get("task_Status");
        TaskStatus updateStatus;

        try {
            updateStatus = TaskStatus.fromString(statusString);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "El estado " + statusString + " no es válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        boolean isValidUpdate = false;

        if(currentTask.getTaskStatus() == TaskStatus.TODO){
           isValidUpdate = updateStatus == TaskStatus.TODO || updateStatus == TaskStatus.INPROGRESS;
        }else if(currentTask.getTaskStatus() == TaskStatus.INPROGRESS){
            isValidUpdate = updateStatus == TaskStatus.BLOCKED || updateStatus == TaskStatus.DONE;
        }else if(currentTask.getTaskStatus() == TaskStatus.BLOCKED){
            isValidUpdate = updateStatus == TaskStatus.DONE || updateStatus == TaskStatus.INPROGRESS;
        }else if(currentTask.getTaskStatus() == TaskStatus.DONE){
            isValidUpdate = updateStatus == TaskStatus.DONE;
        }

        if (!isValidUpdate) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Mensaje", "El estado " + updateStatus + " no es válido para la tarea actual con estado " + currentTask.getTaskStatus());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }else{
            Map<String, String> statusUpdate = new HashMap<>();
            statusUpdate.put("Mensaje", "El estado de la Tarea:  " + currentTask.getName() + " del Projecto: " + currentTask.getProject().getName() +
                    " ha sido actualizado correctamente al estado: " + updateStatus);

            currentTask.setTaskStatus(updateStatus);
            taskServices.saveTaskUpdateStatus(currentTask);
           return  ResponseEntity.status(HttpStatus.CREATED).body(statusUpdate);
        }

    }



}
