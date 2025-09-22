package com.coherentsolutions.spftaskmanagementsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "taskapp")
public class AppProperties {
    private String defaultPriority = "MEDIUM";
    private boolean notificationsEnabled = true;
    private int maxTitleLength = 100;

    @NotBlank
    private String environment;

    @Email
    @NotBlank
    private String adminEmail;

    private List<SampleTask> sampleTasks = new ArrayList<>();

    @Data
    public static class SampleTask {
        private String title;
        private String assigneeEmail;
        private String description;
        private String status;
        private String priority;
    }
}
