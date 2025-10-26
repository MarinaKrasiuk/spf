package com.coherentsolutions.spftaskmanagementsystem.repository;

import com.coherentsolutions.spftaskmanagementsystem.models.Status;
import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {
    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public Task save(Task task) {

        if (task.getId() == null) {
            boolean titleExists = store.values().stream()
                    .anyMatch(existing -> existing.getTitle().equalsIgnoreCase(task.getTitle()));
            if (titleExists) {
                throw new IllegalArgumentException("Task title must be unique.");
            }
            task.setId(counter.incrementAndGet());
        }
        store.put(task.getId(), task);
        return task;
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Task> findAllSortedByCreatedAt() {
        return store.values().stream()
                .sorted(Comparator.comparing(Task::getCreatedAt))
                .toList();
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }

    public List<Task> findByAssigneeAndStatus(String email, Status status) {
        return store.values().stream()
                .filter(t -> email == null ||
                        (t.getAssigneeEmail() != null && t.getAssigneeEmail().equalsIgnoreCase(email)))
                .toList();
    }

    public Optional<Task> findByTitle(String title) {
        return store.values().stream()
                .filter(t -> t.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }
}
