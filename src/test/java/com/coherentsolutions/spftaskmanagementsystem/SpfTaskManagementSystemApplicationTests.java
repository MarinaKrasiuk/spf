package com.coherentsolutions.spftaskmanagementsystem;

import com.coherentsolutions.spftaskmanagementsystem.config.TaskService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.coherentsolutions.spftaskmanagementsystem.models.*;
import java.util.List;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SpfTaskManagementSystemApplicationTests {
    private final TaskService taskService;

    SpfTaskManagementSystemApplicationTests(TaskService taskService) {
        this.taskService = taskService;
    }

    @Test
    void contextLoads() {
        Assertions.assertThat(taskService).isNotNull();
    }

    @Test
    void createAndRetrieveTask() {
        Task newTask = Task.builder()
                .title("Integration Test Task")
                .description("Created in integration test")
                .status(Status.TODO)
                .priority(Priority.MEDIUM)
                .assigneeEmail("tester@example.com")
                .build();

        Task savedTask = taskService.create(newTask);

        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getCreatedAt()).isNotNull();

        Task retrieved = taskService.findById(savedTask.getId()).orElseThrow();
        assertThat(retrieved.getTitle()).isEqualTo("Integration Test Task");
    }

    @Test
    void updateTaskStatus() {
        Task task = taskService.create(Task.builder()
                .title("Status Update Test")
                .status(Status.TODO)
                .priority(Priority.LOW)
                .assigneeEmail("status@example.com")
                .build());

        task.setStatus(Status.IN_PROGRESS);
        Task updated = taskService.update(task.getId(), task);

        assertThat(updated.getStatus()).isEqualTo(Status.IN_PROGRESS);
    }

    @Test
    void deleteTask() {
        Task task = taskService.create(Task.builder()
                .title("Delete Me")
                .status(Status.TODO)
                .priority(Priority.LOW)
                .assigneeEmail("delete@example.com")
                .build());

        Long id = task.getId();
        taskService.delete(id);

        assertThat(taskService.findById(id)).isEmpty();
    }

    @Test
    void searchTasksByAssignee() {
        taskService.create(Task.builder()
                .title("Search Test")
                .status(Status.TODO)
                .priority(Priority.HIGH)
                .assigneeEmail("search@example.com")
                .build());

        List<Task> results = taskService.search("search@example.com", Status.TODO);
        assertThat(results).isNotEmpty();
    }
}
