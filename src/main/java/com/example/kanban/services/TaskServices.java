package com.example.kanban.services;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.entitys.TaskStatus;
import com.example.kanban.repositorys.ProjectRepository;
import com.example.kanban.repositorys.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Optional<Task> getTaskById(UUID id){
        return this.taskRepository.findById(id);

    }

    public void deleteTaskById(UUID id){
        this.taskRepository.deleteById(id);
    }

    public Task createTask(Task task){
        return this.taskRepository.save(task);
    }

    public List<Task> getAllTask(){
        return this.taskRepository.findAll();
    }

    public Task saveTaskUpdateStatus(Task task){
        return this.taskRepository.save(task);
    }

    public Task createTaskk(UUID project_id, Task task) throws ChangeSetPersister.NotFoundException {

       Project project =  projectRepository.findById(project_id)
               .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        task.setProject(project);
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        Task task =  taskRepository.findById(taskId).orElse(null);
        TaskStatus currentStatus = task.getTaskStatus();

        if (!currentStatus.isValidStatus(newStatus)) {
            throw new IllegalArgumentException("No es posible asignar al estado " + newStatus +
                    " una tarea con estado " + currentStatus);
        }

        task.setTaskStatus(newStatus);
        return task;
    }

    public Task getByStatus(TaskStatus status){
        return this.taskRepository.findByStatus(status);
    }

}
