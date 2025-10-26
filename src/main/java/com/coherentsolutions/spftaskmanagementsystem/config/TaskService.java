package com.coherentsolutions.spftaskmanagementsystem.config;

import com.coherentsolutions.spftaskmanagementsystem.models.Status;
import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import com.coherentsolutions.spftaskmanagementsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repo;
    private final NotificationService notifier;
    private final AuditService audit;

    public Task create(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        Task saved = repo.save(task);
        notifier.notifyAssignment(saved);
        audit.log("Created task " + saved.getId());
        return saved;
    }

    public Optional<Task> findById(Long id) {
        return repo.findById(id);
    }

    public List<Task> findAll() {
        return repo.findAll();
    }

    public Task update(Long id, Task updated) {
        Task existing = repo.findById(id).orElseThrow();

        if (existing.getStatus() == Status.DONE && updated.getStatus() == Status.TODO) {
            throw new IllegalStateException("Cannot move task from DONE back to TODO");
        }

        updated.setId(id);
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setUpdatedAt(LocalDateTime.now());
        Task saved = repo.save(updated);
        notifier.notifyStatusChange(saved);
        audit.log("Updated task " + saved.getId());
        return saved;
    }

    public void delete(Long id) {
        repo.findById(id).orElseThrow(); // ensure exists
        repo.delete(id);
        notifier.notifyDeletion(id);
        audit.log("Deleted task " + id);
    }

    public List<Task> search(String email, Status status) {
        return repo.findByAssigneeAndStatus(email, status);
    }
}
