package com.example.kanban.repositorys;

import com.example.kanban.entitys.Project;
import com.example.kanban.entitys.Task;
import com.example.kanban.entitys.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("SELECT t FROM Task t WHERE t.taskStatus = :status")
    Task findByStatus(TaskStatus status);
}
