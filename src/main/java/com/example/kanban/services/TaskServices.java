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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<Task> getAllTaskexpired(UUID project_id) throws ChangeSetPersister.NotFoundException {
        Project project = this.projectRepository.findById(project_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        LocalDateTime dateToday = LocalDateTime.now();
        return project.getTasks().stream()
                .filter(task -> dateToday.isAfter(task.getDueDate()))
                .collect(Collectors.toList());
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

    public Task getByStatus(TaskStatus status){
        return this.taskRepository.findByStatus(status);
    }

    /*public Boolean isValidUpdate(){
        Task currentTask = taskServices.getTaskById(id).orElse(null);

        boolean isValidUpdate = false;

        if(currentTask.getTaskStatus() == TaskStatus.TODO){
            isValidUpdate = updateStatus == TaskStatus.TODO || updateStatus == TaskStatus.INPROGRESS;
        }else if(currentTask.getTaskStatus() == TaskStatus.INPROGRESS){
            isValidUpdate = updateStatus == TaskStatus.BLOCKED || updateStatus == TaskStatus.DONE;
        }else if(currentTask.getTaskStatus() == TaskStatus.BLOCKED){
            isValidUpdate = updateStatus == TaskStatus.DONE || updateStatus == TaskStatus.INPROGRESS;
        }else if(currentTask.getTaskStatus() == TaskStatus.DONE){
            isValidUpdate = updateStatus == TaskStatus.DONE;
        }

        return isValidUpdate;
    }*/

}
