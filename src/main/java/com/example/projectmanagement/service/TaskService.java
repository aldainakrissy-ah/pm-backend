package com.example.projectmanagement.service;

import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.model.Project;
import com.example.projectmanagement.repository.TaskRepository;
import com.example.projectmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service class for managing tasks within projects.
 * <p>
 * Provides business logic for creating tasks, associating them with projects,
 * and retrieving tasks with filtering and sorting options.
 * </p>
 *
 * <ul>
 *   <li>{@link #createTask(Long, Task)}: Creates a new task under a specific project and logs a notification asynchronously.</li>
 *   <li>{@link #getTasks(Long, LocalDate, LocalDate, String, Pageable)}: Retrieves a paginated and sorted list of tasks for a project, filtered by date range.</li>
 * </ul>
 *
 * Dependencies:
 * <ul>
 *   <li>{@link TaskRepository}: For CRUD operations on tasks.</li>
 *   <li>{@link ProjectRepository}: For retrieving project information.</li>
 * </ul>
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Creates a new task associated with the specified project.
     * Also logs an email notification asynchronously.
     *
     * @param projectId the ID of the project to associate the task with
     * @param task the task entity to create
     * @return the saved Task entity
     * @throws java.util.NoSuchElementException if the project does not exist
     */
    public Task createTask(Long projectId, Task task) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project with id " + projectId + " not found"));
        task.setProject(project);
        Task saved = taskRepository.save(task);

        executor.submit(() -> System.out.println("Logging email notification to: " + task.getAssignee()));
        return saved;
    }

    /**
     * Retrieves a paginated list of tasks for a given project, filtered by date range and sorted by the specified field.
     *
     * @param projectId the ID of the project
     * @param start the start date for filtering tasks
     * @param end the end date for filtering tasks
     * @param sortBy the field to sort by
     * @param pageable the pagination information
     * @return a page of Task entities matching the criteria
     */
    public Page<Task> getTasks(Long projectId, LocalDate start, LocalDate end, String sortBy, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return taskRepository.findByProjectAndDateRange(projectId, start, end, sortedPageable);
    }
}