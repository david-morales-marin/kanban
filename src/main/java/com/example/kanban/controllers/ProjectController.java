package com.example.kanban.controllers;

import com.example.kanban.entitys.Project;
import com.example.kanban.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping()
    public List<Project> getProjects(){
        return this.projectServices.getListaProject();
    }

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable("id") Long id){
        return this.projectServices.getProject(id);
    }

    @PostMapping()
    public Project creatreProject(@RequestBody Project project){
        return this.projectServices.createProject(project);
    }

    @PutMapping("/{id}")
    public Project putProject(@RequestBody Project project){
        return this.projectServices.putProject(project, project.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") Long id){
         this.projectServices.deleteProject(id);
    }

}