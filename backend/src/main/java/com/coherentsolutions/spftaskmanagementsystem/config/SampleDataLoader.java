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
    private final AppProperties appProperties;

    public SampleDataLoader(TaskRepository taskRepository, AppProperties appProperties) {
        this.taskRepository = taskRepository;
        this.appProperties = appProperties;
    }

    @PostConstruct
    public void init() {
        for (AppProperties.SampleTask s : appProperties.getSampleTasks()) {
            Task task = Task.builder()
                    .title(s.getTitle())
                    .assigneeEmail(s.getAssigneeEmail())
                    .description(s.getDescription())
                    .status(Status.valueOf(s.getStatus()))
                    .priority(Priority.valueOf(s.getPriority()))
                    .build();
            taskRepository.save(task);
        }
    }
}
