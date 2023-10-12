package com.example.kanban.controllers;

import com.example.kanban.entitys.*;
import com.example.kanban.models.ProjectBoardResponse;
import com.example.kanban.services.ProjectServices;
import com.example.kanban.services.TaskServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/v1/projects")
@Api(tags = "Projects", description = "Controller Project")
public class ProjectController {

    @Autowired
    private ProjectServices projectServices;

    @Autowired
    private TaskServices taskServices;

    @Autowired()
    private ProjectController(ProjectServices projectServices){
        this.projectServices = projectServices;
    }

   // @PreAuthorize("hasRole('USER')") //autoriza que tipo de cosas quiere hacer
    @SecurityRequirement(name = "bearerAuth")
    @ApiOperation(value = "Obtener todas los projectos", notes = "Devuelve una lista de todos los projectos creados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Su respuesta ha sido exitosa."),
            @ApiResponse(code = 400, message = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(code = 301, message = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(code = 403, message = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(code= 500, message = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @GetMapping("/list")
    public List<Project> getProjects(){
        return this.projectServices.getListaProject();
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Project>> getAllProjects(
            @RequestParam(defaultValue = "0")
            int page, @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Project> projects = projectServices.getAllProjects(pageRequest);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable("id") UUID id){
        return this.projectServices.getProject(id);
    }

    @PostMapping()
    public ResponseEntity<String> createProject1(@RequestBody Project project) {


        if (project.getName() == null || project.getName().isEmpty()) {

            return new ResponseEntity<>("El nombre del projecto es obligatorio", HttpStatus.BAD_REQUEST);
        }
                   project.setProjectStatus(ProjectStatus.ACTIVE);
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

    @GetMapping("/{id}/board")
    public ResponseEntity<ProjectBoardResponse> getTaskByProject(@PathVariable("id") UUID id){
        ProjectBoardResponse result = projectServices.getTaskByProject(id);

        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

            return ResponseEntity.ok(result);
        }

    }

}