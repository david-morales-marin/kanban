package com.example.kanban.servicesTest;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.repositorys.ProjectRepository;
import com.example.kanban.repositorys.TaskRepository;
import com.example.kanban.services.ProjectServices;
import com.example.kanban.services.TaskServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServicesTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskServices taskServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void SuccessGetTaskById() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> result = taskServices.getTaskById(taskId);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(task, result.orElse(null));
    }

    @Test
    public void SuccessDeleteTaskById(){
        UUID taskId = UUID.randomUUID();
        Task task = new Task();

    when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskServices.deleteTaskById(taskId);

        verify(taskRepository, Mockito.times(1)).deleteById(taskId);
    }

    @Test
    public void testGetAllTaskExpired() throws ChangeSetPersister.NotFoundException {
        UUID projectId = UUID.randomUUID();
        Project mockProject = new Project();
        mockProject.setId(projectId);

        Task task1 = new Task();
        task1.setDueDate(LocalDateTime.now().minusDays(1));

        Task task2 = new Task();
        task2.setDueDate(LocalDateTime.now().plusDays(1));

        List<Task> projectTasks = new ArrayList<>();
        projectTasks.add(task1);
        projectTasks.add(task2);

        mockProject.setTasks(projectTasks);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockProject));

        List<Task> expiredTasks = taskServices.getAllTaskexpired(projectId);

        assertEquals(1, expiredTasks.size());
        assertEquals(task1, expiredTasks.get(0));

    }

    @Test
    public void SuccessGetAllTask(){
        Task task1 = new Task();
        Task task2 = new Task();

        List<Task> task = Arrays.asList(task1,task2);

        when(taskRepository.findAll()).thenReturn(new ArrayList<>(task));

        List<Task> result = taskServices.getAllTask();

        assertNotNull(result);
        assertEquals(2 , result.size());
    }

    @Test
    public void SuccessSaveTaskUpdateStatus(){
         Task task = new Task();

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskServices.saveTaskUpdateStatus(task);

        assertNotNull(result);
        assertEquals(task , result);

    }
}
