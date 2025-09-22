package com.coherentsolutions.spftaskmanagementsystem.repository;

import com.coherentsolutions.spftaskmanagementsystem.models.Status;
import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {
    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(counter.incrementAndGet());
        }
        store.put(task.getId(), task);
        return task;
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(Long id) {
        store.remove(id);
    }

    public List<Task> findByAssigneeAndStatus(String email, Status status) {
        return store.values().stream()
                .filter(t -> (email == null || t.getAssigneeEmail().equalsIgnoreCase(email)))
                .filter(t -> (status == null || t.getStatus() == status))
                .toList();
    }


}
