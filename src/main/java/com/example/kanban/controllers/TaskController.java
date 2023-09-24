package com.example.kanban.controllers;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.entitys.TaskStatus;
import com.example.kanban.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/* DELETE -> /v1/tasks/{id} // eliminar un Task
   GET -> /v1/tasks/{id} // obtener un Task */
@RestController()
@RequestMapping("/v1")
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

    @PostMapping("/projects/{project_id}/tasks")
    public ResponseEntity<Task> createTaskk(@PathVariable UUID project_id, @RequestBody Task task) throws
            ChangeSetPersister.NotFoundException {

        task.setTaskStatus(TaskStatus.TODO);
        Task createdTask =  taskServices.createTaskk(project_id, task);   //taskServices.createTask(projectId, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

  /*  @PutMapping("/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable UUID taskId,
            @RequestParam TaskStatus newStatus) {

        Task updatedTask = taskServices.updateTaskStatus(taskId, newStatus);
        if (updatedTask != null) {
            return ResponseEntity.ok("Estado de la tarea actualizado con éxito.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

   /* @PutMapping("/{taskId}/status")
    public ResponseEntity<Object> updateTaskStatus(
            @PathVariable UUID taskId,
            @RequestParam TaskStatus taskStatus) {
        try {
            Task updatedTask = taskServices.updateTaskStatus(taskId, taskStatus);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            String errorMessage = "No es posible asignar al estado " + taskStatus +
                    " una tarea con estado " + taskServices.getTaskById(taskId).get().getTaskStatus();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    } */

    @PutMapping("/{taskId}/status")
    public ResponseEntity<Object> updateTaskStatus(
            @PathVariable UUID taskId,
            @RequestParam TaskStatus newStatus) {
        try {
            Task updatedTask = taskServices.updateTaskStatus(taskId, newStatus);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
