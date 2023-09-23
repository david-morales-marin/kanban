package com.example.kanban.repositorys;

import com.example.kanban.entitys.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

  //  public Task createTask()
}
