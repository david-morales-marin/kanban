package com.example.kanban.services;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.repositorys.ProjectRepository;
import com.example.kanban.repositorys.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServices {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public ProjectServices (ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }
    public List<Project> getListaProject(){
        return this.projectRepository.findAll();
    }

    public Page<Project> getAllProjects(Pageable pageable){
        return projectRepository.findAll(pageable);
    }

    public Optional<Project> getProject(UUID id){
        return this.projectRepository.findById(id);
    }

    public Project createProject(Project nuevoProject){

        return this.projectRepository.save(nuevoProject);
    }

    public void deleteProject(UUID id){
        this.projectRepository.deleteById(id);
      // return "Se elimino el projecto correctamente";
    }

    public Project putProject(Project project, UUID id){

        Project project1 = projectRepository.findById(id).get();
        project1.setName(project.getName());

        return this.projectRepository.save(project1);

    }

    public Project finById(UUID id){
        return this.projectRepository.findById(id).orElse(null);
    }


}
