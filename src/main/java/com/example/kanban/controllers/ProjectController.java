package com.example.kanban.controllers;

import com.example.kanban.entitys.*;
import com.example.kanban.models.ProjectBoardResponse;
import com.example.kanban.services.ProjectServices;
import com.example.kanban.services.TaskServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/projects")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Project controller", description = "Los metodos para la creación de los projectos")
public class ProjectController {

    @Autowired
    private ProjectServices projectServices;

    @Autowired
    private TaskServices taskServices;

    @Autowired()
    private ProjectController(ProjectServices projectServices){
        this.projectServices = projectServices;
    }

    @Operation(summary = "Obtener todos los projectos",
            description = "Devuelve una lista de todos los projectos creados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Su respuesta ha sido exitosa."),
            @ApiResponse(responseCode = "400", description = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(responseCode = "301", description = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(responseCode = "403", description = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(responseCode = "500", description = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @GetMapping("/")
    public List<Project> getProjects(){
        return this.projectServices.getListaProject();
    }

    @Operation(summary = "Obtener todos los projectos de una manera paginada ",
               description = "Devuelve una lista de todos los projectos creados de forma paginada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Su respuesta ha sido exitosa."),
            @ApiResponse(responseCode = "400", description = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(responseCode = "301", description = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(responseCode = "403", description = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(responseCode = "500", description = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @GetMapping("/pageable")
    public ResponseEntity<Page<Project>> getAllProjects(
            @RequestParam(defaultValue = "0")
            int page, @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Project> projects = projectServices.getAllProjects(pageRequest);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Obtiene un solo projecto",
            description = "Devuelve un projecto que se busca según si ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Su respuesta ha sido exitosa."),
            @ApiResponse(responseCode = "400", description = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(responseCode = "301", description = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(responseCode = "403", description = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(responseCode = "500", description = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable("id") UUID id){
        return this.projectServices.getProject(id);
    }

    @Operation(summary = "Creacion de un projecto",
            description = "Crea un projecto el cual debe de tener por obligación el nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "El proyecto ha sido creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(responseCode = "301", description = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(responseCode = "403", description = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(responseCode = "500", description = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @PostMapping()
    public ResponseEntity<String> createProject1(@RequestBody Project project) {

        if (project.getName() == null || project.getName().isEmpty()) {
            return new ResponseEntity<>("El nombre del projecto es obligatorio", HttpStatus.BAD_REQUEST);
        }
                   project.setProjectStatus(ProjectStatus.ACTIVE);
                   this.projectServices.createProject(project);
        return new ResponseEntity<>("Proyecto creado con éxito", HttpStatus.CREATED);

    }

    @Operation(summary = "Actualiza projecto",
            description = "Actualiza un projecto segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "La actualización ha sido exitosa."),
            @ApiResponse(responseCode = "400", description = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(responseCode = "301", description = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(responseCode = "403", description = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(responseCode = "500", description = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @PutMapping("/{id}")
    public Project putProject(@RequestBody Project project){
        return this.projectServices.putProject(project, project.getId());
    }

    @Operation(summary = "Elimina un projecto",
            description = "Elimina un projecto por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "El projecto ha sido eliminado correctamente."),
            @ApiResponse(responseCode = "400", description = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(responseCode = "301", description = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(responseCode = "403", description = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(responseCode = "500", description = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") UUID id){
         this.projectServices.deleteProject(id);
    }

    @Operation(summary = "Obtener todas las tareas por projecto",
            description = "Obtiene todas las tareas de un projecto, organizado por el estado de las tareas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Su respuesta ha sido exitosa."),
            @ApiResponse(responseCode = "400", description = "Bad Request, Algo ingresaste mal. Verifica la información."),
            @ApiResponse(responseCode = "301", description = "Credenciales erroneas o permisos no otorgados."),
            @ApiResponse(responseCode = "403", description = "Credenciales insuficientes para visualizar la lista de los projectos."),
            @ApiResponse(responseCode = "500", description = "Error inesperado del sistema, comuniquese con el proveedor")
    })
    @GetMapping("/{id}/board")
    public ResponseEntity<ProjectBoardResponse> getProjectByFullTask(@PathVariable("id") UUID id){
        ProjectBoardResponse result = projectServices.getTaskByProject(id);

        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

            return ResponseEntity.ok(result);
        }

    }

}