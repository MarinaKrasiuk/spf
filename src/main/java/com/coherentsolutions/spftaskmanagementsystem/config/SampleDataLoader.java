package com.coherentsolutions.spftaskmanagementsystem.config;

import com.coherentsolutions.spftaskmanagementsystem.models.Priority;
import com.coherentsolutions.spftaskmanagementsystem.models.Status;
import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import com.coherentsolutions.spftaskmanagementsystem.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader {

    private final TaskRepository taskRepository;
    private final TaskProperties taskProperties;


    public SampleDataLoader(TaskRepository taskRepository, TaskProperties taskProperties) {
        this.taskRepository = taskRepository;
        this.taskProperties = taskProperties;
    }

    @PostConstruct
    public void init() {
        if (taskProperties.isSampleDataEnabled()) {
            for (TaskProperties.SampleTask s : taskProperties.getSampleTasks()) {
                Task task = Task.builder()
                        .title(s.getTitle())
                        .assigneeEmail(s.getAssigneeEmail())
                        .description(s.getDescription())
                        .status(Status.fromString(s.getStatus().getValue()))
                        .priority(Priority.fromString(s.getPriority().getValue()))
                        .createdAt(s.getCreatedAt())
                        .updatedAt(s.getUpdatedAt())
                        .build();
                taskRepository.save(task);
            }
        }
    }
}
