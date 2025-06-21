package com.example.projectmanagement.controller;

import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task createTask(@PathVariable Long projectId, @RequestBody Task task) {
        return taskService.createTask(projectId, task);
    }

    @GetMapping
    public Page<Task> getTasks(
        @PathVariable Long projectId,
        @RequestParam String startDate,
        @RequestParam String endDate,
        @RequestParam(defaultValue = "priority") String sortBy,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return taskService.getTasks(
            projectId,
            LocalDate.parse(startDate),
            LocalDate.parse(endDate),
            sortBy,
            PageRequest.of(page, size)
        );
    }
}