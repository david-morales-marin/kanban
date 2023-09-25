package com.example.kanban.controllers;

import com.example.kanban.entitys.Task;
import com.example.kanban.entitys.TaskStatus;
import com.example.kanban.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
            @RequestBody TaskStatus updateRequest) {

        Task currentTask = taskServices.getTaskById(id).orElseGet(null);
        if (currentTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean isValidUpdate = false;

        switch (updateRequest) {
            case TODO:
                isValidUpdate = currentTask.getTaskStatus() == TaskStatus.INPROGRESS;
                break;
            case INPROGRESS:
                isValidUpdate = currentTask.getTaskStatus() == TaskStatus.DONE ||
                        currentTask.getTaskStatus() == TaskStatus.BLOCKED;
                break;
            case BLOCKED:
                isValidUpdate = currentTask.getTaskStatus() == TaskStatus.INPROGRESS ||
                        currentTask.getTaskStatus() == TaskStatus.DONE;
                break;
            case DONE:
                /* DONE no se actualiza a ningún estado, así que no hay condiciones aquí.
                /igual preguntar al profe si deberia colocar alguna condicion en DONE*/
                isValidUpdate = true;
                break;
            default:
                break;
        }

        if (!isValidUpdate) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "El estado " + updateRequest + " no es válido para la tarea actual con estado " + currentTask.getTaskStatus());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        currentTask.setTaskStatus(updateRequest);
        this.taskServices.saveTaskUpdateStatus(currentTask);

        return ResponseEntity.ok().build();
    }

}
