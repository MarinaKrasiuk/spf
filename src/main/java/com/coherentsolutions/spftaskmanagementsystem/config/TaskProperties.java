package com.coherentsolutions.spftaskmanagementsystem.config;

import com.coherentsolutions.spftaskmanagementsystem.models.Priority;
import com.coherentsolutions.spftaskmanagementsystem.models.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Binds and validates externalized configuration for Task Management System.
 * Prefix: 'spf.task'
 *
 * Properties:
 * - defaultPriority: Default task priority (LOW, MEDIUM, HIGH, CRITICAL)
 * - notificationsEnabled: Toggle for system notifications
 * - maxTitleLength: Maximum characters for a task title
 * - environment: Active deployment environment (e.g., dev, prod)
 * - adminEmail: System administrator contact email
 * - sampleTasks: List of sample task definitions (see SampleTask)
 */

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "spf.task")
public class TaskProperties {
    private String defaultPriority = "MEDIUM";
    private boolean notificationsEnabled = true;
    private boolean sampleDataEnabled = false;
    private int maxTitleLength = 100;

    @NotBlank
    private String environment;

    @Email
    @NotBlank
    private String adminEmail;

    @Valid
    private List<SampleTask> sampleTasks = new ArrayList<>();

    @Data
    public static class SampleTask {
        @NotBlank
        private String title;

        @Email
        @NotBlank
        private String assigneeEmail;

        @Size(max = 1000)
        private String description;

        @NotNull
        private Status status;

        @NotNull
        private Priority priority;

        @NotNull
        private LocalDateTime createdAt;

        @NotNull
        private LocalDateTime updatedAt;
    }
}