package com.example.kanban.controllers;

import com.example.kanban.entitys.Pagination;
import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.services.ProjectServices;
import com.example.kanban.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
POST -> /v1/projects // crear un Project
PUT -> /v1/projects/{id} // editar un Project
DELETE -> /v1/projects/{id} // eliminar un Project
GET -> /v1/projects/{id} // obtener un Project por id */
@RestController()
@RequestMapping("/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectServices projectServices;

    @Autowired
    private TaskServices taskServices;

    @Autowired()
    private ProjectController(ProjectServices projectServices){
        this.projectServices = projectServices;
    }

    @GetMapping("/list")
    public List<Project> getProjects(){
        return this.projectServices.getListaProject();
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Project>> getAllProjects(
            @RequestParam(defaultValue = "0")
            int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Project> projects = projectServices.getAllProjects(pageRequest);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable("id") UUID id){
        return this.projectServices.getProject(id);
    }

    @PostMapping()
    public Project createProject(@RequestBody Project project){
        return this.projectServices.createProject(project);
    }

    @PostMapping("/pruebaPost")
    public ResponseEntity<String> createProject1(@RequestBody Project project) {

        if (project.getName().isEmpty() || project.getName() == "" ){
          //  System.out.println("Advertencia: El nombre del proyecto es obligatorio");
            return new ResponseEntity<>("El nombre del projecto es obligatorio", HttpStatus.BAD_REQUEST);
        }

                   this.projectServices.createProject(project);
        return new ResponseEntity<>("Proyecto creado con éxito", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public Project putProject(@RequestBody Project project){
        return this.projectServices.putProject(project, project.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") UUID id){
         this.projectServices.deleteProject(id);
    }

    //creacion task en proyect
    //POST /v1/projects/{id}/tasks
   /* @PostMapping("/{id}/tasks")
    public Task createTask(@PathVariable("project_id") UUID id, @RequestBody Task task){
        return this.projectServices.createTask( task);
    }*/
    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<String> createTaskInProject(
            @PathVariable UUID projectId,
            @RequestBody Task task) {

        // Buscar el proyecto por su ID
        Project project = projectServices.finById(projectId);// findProjectById(projectId);

        if (project == null) {
            return new ResponseEntity<>("Proyecto no encontrado", HttpStatus.NOT_FOUND);
        }

        // Asignar la tarea al proyecto
        project.getTasks().add(task); //getTasks().add(task);

        // Guardar el proyecto con la nueva tarea
        projectServices.createProject(project);     //updateProject(project);

        // Crear la tarea en la base de datos
        Task createdTask =  taskServices.createTask(task); //taskService.//createTask(task);

                   this.projectServices.createProject(project);
        return new ResponseEntity<>("Tarea creada con éxito", HttpStatus.CREATED);
    }

    //tareas
    @GetMapping("/{projectId}/board")
    public ResponseEntity<List<Task>> getTasksInProject(@PathVariable UUID projectId) {
        // Buscar el proyecto por su ID
        Project project = projectServices.finById(projectId); //.findProjectById(projectId);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Obtener la lista de tareas del proyecto
        List<Task> tasks = project.getTasks();
         //this.taskServices.getAllTask();
        this.projectServices.createProject(project);
        return new ResponseEntity<>(tasks, HttpStatus.CREATED );//HttpStatus.OK);
    }

}