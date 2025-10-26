package com.coherentsolutions.spftaskmanagementsystem.web;

import com.coherentsolutions.spftaskmanagementsystem.config.TaskService;
import com.coherentsolutions.spftaskmanagementsystem.models.Status;
import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService service) {
        this.taskService = service;
    }

    @GetMapping
    public List<Task> all() {
        return taskService.findAllSortedByCreatedAt();
    }

    @GetMapping("/{id}")
    public Task one(@PathVariable Long id) { return taskService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Task> create(@Valid @RequestBody Task task) {
        Task createdTask = taskService.create(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTask.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdTask);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @Valid @RequestBody Task task) {
        return taskService.update(id, task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @GetMapping("/search")
    public List<Task> search(
            @RequestParam(required = false) String assigneeEmail,
            @RequestParam(required = false) Status status) {
        return taskService.search(assigneeEmail, status);
    }
}
