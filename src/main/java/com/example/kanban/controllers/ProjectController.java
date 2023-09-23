package com.example.kanban.controllers;

import com.example.kanban.entitys.Pagination;
import com.example.kanban.entitys.Project;
import com.example.kanban.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    ProjectServices projectServices;

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

        if (project.getName().isEmpty() ){
            return new ResponseEntity<>("El nombre del projecto es obligatorio", HttpStatus.BAD_REQUEST);
        }

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

}