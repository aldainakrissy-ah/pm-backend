package com.example.projectmanagement.service;

import com.example.projectmanagement.model.Project;
import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.repository.ProjectRepository;
import com.example.projectmanagement.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldSaveTaskAndReturnSavedTask() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        Task task = new Task();
        task.setAssignee("test@example.com");

        Task savedTask = new Task();
        savedTask.setId(10L);
        savedTask.setAssignee("test@example.com");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.save(task)).thenReturn(savedTask);

        Task result = taskService.createTask(projectId, task);

        assertEquals(savedTask, result);
        assertEquals(project, task.getProject());
        verify(projectRepository).findById(projectId);
        verify(taskRepository).save(task);
    }

    @Test
    void createTask_shouldThrowExceptionIfProjectNotFound() {
        Long projectId = 2L;
        Task task = new Task();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> taskService.createTask(projectId, task));
        verify(projectRepository).findById(projectId);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void getTasks_shouldReturnPageOfTasks() {
        Long projectId = 1L;
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        String sortBy = "dueDate";
        Pageable pageable = PageRequest.of(0, 10);

        List<Task> tasks = Arrays.asList(new Task(), new Task());
        Page<Task> page = new PageImpl<>(tasks);

        when(taskRepository.findByProjectAndDateRange(eq(projectId), eq(start), eq(end), any(Pageable.class)))
                .thenReturn(page);

        Page<Task> result = taskService.getTasks(projectId, start, end, sortBy, pageable);

        assertEquals(2, result.getContent().size());
        verify(taskRepository).findByProjectAndDateRange(eq(projectId), eq(start), eq(end), any(Pageable.class));
    }

    @Test
    void getProjects_shouldReturnAllProjects() {
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = taskService.getProjects();

        assertEquals(2, result.size());
        verify(projectRepository).findAll();
    }
}