package com.coherentsolutions.spftaskmanagementsystem.config;

import com.coherentsolutions.spftaskmanagementsystem.models.Priority;
import com.coherentsolutions.spftaskmanagementsystem.models.Status;
import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import com.coherentsolutions.spftaskmanagementsystem.repository.TaskRepository;
import com.coherentsolutions.spftaskmanagementsystem.service.AuditService;
import com.coherentsolutions.spftaskmanagementsystem.service.NotificationService;
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
        if (repo.findAllSortedByCreatedAt().stream()
                .anyMatch(t -> t.getTitle().equalsIgnoreCase(task.getTitle()))) {
            throw new IllegalArgumentException("Task title must be unique");
        }
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

    public List<Task> findAllSortedByCreatedAt() {
        return repo.findAllSortedByCreatedAt();
    }

    public Task update(Long id, Task updated) {
        Task existing = repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Task with id " + id + " not found"));

        if (!existing.getTitle().equalsIgnoreCase(updated.getTitle()) &&
                repo.findAllSortedByCreatedAt().stream()
                        .anyMatch(t -> t.getTitle().equalsIgnoreCase(updated.getTitle()))) {
            throw new IllegalArgumentException("Task title must be unique");
        }

        if (!isValidStatusTransition(existing.getStatus(), updated.getStatus())) {
            throw new IllegalStateException("Invalid status transition: " +
                    existing.getStatus() + " → " + updated.getStatus());
        }

        if ((updated.getPriority() == Priority.HIGH || updated.getPriority() == Priority.CRITICAL)
                && (updated.getAssigneeEmail() == null || updated.getAssigneeEmail().isBlank())) {
            throw new IllegalArgumentException("High or Critical priority tasks must have a valid assignee email");
        }

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        existing.setPriority(updated.getPriority());
        existing.setAssigneeEmail(updated.getAssigneeEmail());
        existing.setUpdatedAt(LocalDateTime.now());
        Task saved = repo.save(existing);

        notifier.notifyStatusChange(saved);
        audit.log("Updated task " + saved.getId());

        return saved;
    }

    public void delete(Long id) {
        repo.findById(id).orElseThrow();
        repo.delete(id);
        notifier.notifyDeletion(id);
        audit.log("Deleted task " + id);
    }

    public List<Task> search(String email, Status status) {
        return repo.findByAssigneeAndStatus(email, status);
    }

    private boolean isValidStatusTransition(Status from, Status to) {
        return (from == Status.TODO && to == Status.IN_PROGRESS)
                || (from == Status.IN_PROGRESS && to == Status.DONE)
                || (from == to); // Allow no-op transitions if required
    }
}
