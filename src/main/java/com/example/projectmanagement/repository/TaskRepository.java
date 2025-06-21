package com.example.projectmanagement.repository;

import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.dueDate BETWEEN :startDate AND :endDate")
    Page<Task> findByProjectAndDateRange(Long projectId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}