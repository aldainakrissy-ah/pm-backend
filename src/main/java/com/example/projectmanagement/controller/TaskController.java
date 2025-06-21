package com.example.projectmanagement.controller;

import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for managing tasks within a specific project.

 * Provides endpoints to create a new task and retrieve tasks for a given project,
 * supporting filtering by date range, sorting, and pagination.
 *
 * Cross-origin requests are allowed from <code>http://localhost:3000</code>.
 * @author Aldaina Krissy Auman-Hernandez
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Creates a new task for the specified project.
     *
     * @param projectId the ID of the project to which the task will be added
     * @param task the task object to be created
     * @return the created Task object
     */
    @PostMapping
    public Task createTask(@PathVariable Long projectId, @RequestBody Task task) {
        return taskService.createTask(projectId, task);
    }

    /**
     * Retrieves a paginated list of tasks for a given project within a specified date range.
     *
     * @param projectId the ID of the project to retrieve tasks for
     * @param startDate the start date (inclusive) for filtering tasks, in ISO format (yyyy-MM-dd)
     * @param endDate the end date (inclusive) for filtering tasks, in ISO format (yyyy-MM-dd)
     * @param sortBy the field to sort the tasks by (default is "priority")
     * @param page the page number to retrieve (zero-based, default is 0)
     * @param size the number of tasks per page (default is 10)
     * @return a {@link Page} of {@link Task} objects matching the criteria
     */
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
            PageRequest.of(page, size, Sort.by(sortBy))
    );
    }
}