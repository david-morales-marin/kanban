package com.example.kanban.services;

import com.example.kanban.entitys.Project;
import com.example.kanban.repositorys.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Project> getProject(Long id){
        return this.projectRepository.findById(id);
    }

    public Project createProject(Project nuevoProject){
        return this.projectRepository.save(nuevoProject);
    }

    public void deleteProject(Long id){
        this.projectRepository.deleteById(id);
<<<<<<< HEAD
      //  return "Se elimino el projecto correctamente";
=======
        //  return "Se elimino el projecto correctamente";
>>>>>>> 895a38e3c167974fdde127385b0327389fe05604
    }

    public Project putProject(Project project, Long id){

        Project project1 = projectRepository.findById(id).get();
        project1.setName(project.getName());

        return this.projectRepository.save(project1);

    }

}
