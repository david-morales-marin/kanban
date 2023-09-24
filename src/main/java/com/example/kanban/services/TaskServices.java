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

    public Task createTaskk(UUID project_id, Task task) throws ChangeSetPersister.NotFoundException {

       Project project =  projectRepository.findById(project_id)
               .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        task.setProject(project);
        return taskRepository.save(task);
    }

  /*  public Task updateTaskStatus(UUID taskId, TaskStatus status) {
        Task task =  taskRepository.findById(taskId).orElse(null); //findTaskById(taskId);

        if (task != null) {
            task.setTaskStatus(status);  //setStatus(newStatus);
        }
        return task;
    }*/

   /* public Task updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        Task taskToUpdate =  taskRepository.findById(taskId).orElse(null);
        if (taskToUpdate != null) {
            TaskStatus currentStatus = taskToUpdate.getTaskStatus();// getStatus();
            if (isValidStatus(currentStatus, newStatus)) {
                taskToUpdate.setTaskStatus(newStatus);
            } else {
                throw new IllegalArgumentException("Transición de estado no válida.");
            }
        }
        return taskToUpdate;
    }*/

    public Task updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        Task task =  taskRepository.findById(taskId).orElse(null);
        TaskStatus currentStatus = task.getTaskStatus();  //.getStatus();

        if (!currentStatus.isValidStatus(newStatus)) {
            throw new IllegalArgumentException("No es posible asignar al estado " + newStatus +
                    " una tarea con estado " + currentStatus);
        }

        // Realiza la actualización del estado aquí
        task.setTaskStatus(newStatus); //setStatus(newStatus);
        // ...
        return task;
    }

   /* private boolean isValidStatus(TaskStatus currentStatus, TaskStatus newStatus) {
        switch (currentStatus) {
            case TODO:
                return newStatus == TaskStatus.INPROGRESS;
            case INPROGRESS:
                return newStatus == TaskStatus.DONE || newStatus == TaskStatus.BLOCKED;
            case BLOCKED:
                return newStatus == TaskStatus.INPROGRESS || newStatus == TaskStatus.DONE;
         //   case DONE:
           //     return newStatus == TaskStatus.BLOCKED; CREO QUE DONE NO SE ACTUALIZA A NADA
            default:
                return false;
        }
    }*/



}
