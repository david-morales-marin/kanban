package com.example.kanban.servicesTest;

import com.example.kanban.entitys.Project;
import com.example.kanban.repositorys.ProjectRepository;
import com.example.kanban.repositorys.TaskRepository;
import com.example.kanban.services.ProjectServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ProjectServicesTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectServices projectServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void SuccessGetListaProject(){
        Project project1 = new Project();
        Project project2 = new Project();

        List<Project> projects = Arrays.asList(project1, project2);

        when(projectRepository.findAll()).thenReturn(new ArrayList<>(projects));

        List<Project> result = projectServices.getListaProject();

        assertNotNull(result);
        assertEquals(2 , result.size());
    }

    @Test
    public void SuccessGetAllProjects() {

        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = Arrays.asList(project1, project2);

        when(projectRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(projects));

        List<Project> result = projectServices.getAllProjects(PageRequest.of(0, 10)).getContent();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void SuccessGetProject() {

        UUID projectId = UUID.randomUUID();
        Project project = new Project();
        project.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Optional<Project> result = projectServices.getProject(projectId);

        assertNotNull(result);
        assertEquals(project, result.orElse(null));
    }

    @Test
    public void SuccessCreateProject(){
        Project project = new Project();

        when(projectRepository.save(project)).thenReturn(project);

        Project result = projectServices.createProject(project);

        assertNotNull(result);
        assertEquals(project , result);
    }

    @Test
    public void SuccessDeleteProject(){
        UUID projectId = UUID.randomUUID();
        Project project = new Project();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectServices.deleteProject(projectId);

        verify(projectRepository, Mockito.times(1)).deleteById(projectId);
    }

    @Test
    public void SuccessPutProject(){
        UUID projectId = UUID.randomUUID();
        Project project = new Project();

        project.setId(projectId);
        project.setName("Project prueba");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        when(projectRepository.save(Mockito.any(Project.class))).thenAnswer(invocation -> {
            Project projectUpdate = invocation.getArgument(0);
            return projectUpdate;
        });

        Project projectUpdate1 = new Project();
        projectUpdate1.setName("Project actualizar Informacion");

        Project updatedProject = projectServices.putProject(projectUpdate1, projectId);

        assertNotNull(updatedProject);
        assertEquals(projectUpdate1.getName(), updatedProject.getName());
    }

   /* @Test
    public void SuccessGetTaskByProject() {
        UUID projectId = UUID.randomUUID();
        Project project = new Project();
        project.setId(projectId);
        project.setName("Test Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

    }*/

}