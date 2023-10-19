package com.example.kanban.services;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.entitys.TaskStatus;
import com.example.kanban.models.BoardResponse;
import com.example.kanban.models.ProjectBoardResponse;
import com.example.kanban.repositorys.ProjectRepository;
import com.example.kanban.repositorys.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    }

    public Project putProject(Project project, UUID id){

        Project project1 = projectRepository.findById(id).get();
        project1.setName(project.getName());

        return this.projectRepository.save(project1);

    }

    public Project finById(UUID id){
        return this.projectRepository.findById(id).orElse(null);
    }

  /*  public ProjectBoardResponse getTaskByProject(UUID id){

        Project project = this.projectRepository.findById(id).orElse(null);

        if(project != null) {
            ProjectBoardResponse result = new ProjectBoardResponse();
            result.setId(project.getId());
            result.setName(project.getName());

            List<Task> tasks = this.taskRepository.findByProjectId(id);
            if(tasks != null){
                List<Task> taskTODO = new ArrayList<>();
                List<Task> taskINPROGRES = new ArrayList<>();
                List<Task> taskBLOCKED = new ArrayList<>();
                List<Task> taskDONE = new ArrayList<>();
                tasks.stream().forEach(task -> {

                    if(task.getTaskStatus().equals(TaskStatus.TODO)){
                        taskTODO.add(task);
                    }else if(task.getTaskStatus().equals(TaskStatus.INPROGRESS)){
                        taskINPROGRES.add(task);
                    }else if(task.getTaskStatus().equals(TaskStatus.BLOCKED)){
                        taskBLOCKED.add(task);
                    }else if(task.getTaskStatus().equals(TaskStatus.DONE)){
                        taskDONE.add(task);
                    }
                });


                BoardResponse boardTODO = new BoardResponse();
                boardTODO.setStatus(TaskStatus.TODO.name());
                boardTODO.setTasks(taskTODO);

                BoardResponse boardINPROGRESS = new BoardResponse();
                boardINPROGRESS.setStatus(TaskStatus.INPROGRESS.name());
                boardINPROGRESS.setTasks(taskINPROGRES);

                BoardResponse boardBLOCKED = new BoardResponse();
                boardBLOCKED.setStatus(TaskStatus.BLOCKED.name());
                boardBLOCKED.setTasks(taskBLOCKED);

                BoardResponse boardDONE = new BoardResponse();
                boardDONE.setStatus(TaskStatus.DONE.name());
                boardDONE.setTasks(taskDONE);

                List<BoardResponse> resultBoard = new ArrayList<>();
                resultBoard.add(boardTODO);
                resultBoard.add(boardINPROGRESS);
                resultBoard.add(boardBLOCKED);
                resultBoard.add(boardDONE);

                result.setBoard(resultBoard);
            }
            return result;
        }

        return  null;
    } */

    //mejora de separar el metodo en metodos mas pequeños
    public ProjectBoardResponse getTaskByProject(UUID id) {
        
        Project project = this.projectRepository.findById(id).orElse(null);

        if (project != null) {
            ProjectBoardResponse result = new ProjectBoardResponse();
            result.setId(project.getId());
            result.setName(project.getName());

            List<Task> tasks = this.taskRepository.findByProjectId(id);

            if (tasks != null) {
                List<BoardResponse> resultBoard = listBoardResponses(tasks);
                result.setBoard(resultBoard);
            }

            return result;
        }

        return null;
    }

    private List<BoardResponse> listBoardResponses(List<Task> tasks) {
        List<BoardResponse> result = new ArrayList<>();
        List<Task> taskTODO = new ArrayList<>();
        List<Task> taskINPROGRESS = new ArrayList<>();
        List<Task> taskBLOCKED = new ArrayList<>();
        List<Task> taskDONE = new ArrayList<>();

        tasks.stream().forEach(task -> {
            if (task.getTaskStatus().equals(TaskStatus.TODO)) {
                taskTODO.add(task);
            } else if (task.getTaskStatus().equals(TaskStatus.INPROGRESS)) {
                taskINPROGRESS.add(task);
            } else if (task.getTaskStatus().equals(TaskStatus.BLOCKED)) {
                taskBLOCKED.add(task);
            } else if (task.getTaskStatus().equals(TaskStatus.DONE)) {
                taskDONE.add(task);
            }
        });

        result.add(statusBoard(TaskStatus.TODO.name(), taskTODO));
        result.add(statusBoard(TaskStatus.INPROGRESS.name(), taskINPROGRESS));
        result.add(statusBoard(TaskStatus.BLOCKED.name(), taskBLOCKED));
        result.add(statusBoard(TaskStatus.DONE.name(), taskDONE));

        return result;
    }

    private BoardResponse statusBoard(String status, List<Task> tasks) {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setStatus(status);
        boardResponse.setTasks(tasks);
        return boardResponse;
    }

}
