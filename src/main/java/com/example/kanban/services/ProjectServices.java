package com.example.kanban.services;

import com.example.kanban.entitys.Project;
import com.example.kanban.repositorys.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServices {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectServices (ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }
    public List<Project> getListaProject(){
        return this.projectRepository.findAll();
    }

    public Optional<Project> getProject(UUID id){
        return this.projectRepository.findById(id);
    }

    public Project createProject(Project nuevoProject){
        return this.projectRepository.save(nuevoProject);
    }

    public void deleteProject(UUID id){
        this.projectRepository.deleteById(id);
      //  return "Se elimino el projecto correctamente";
    }

    public Project putProject(Project project, UUID id){

        Project project1 = projectRepository.findById(id).get();
        project1.setName(project.getName());

        return this.projectRepository.save(project1);

    }

}
