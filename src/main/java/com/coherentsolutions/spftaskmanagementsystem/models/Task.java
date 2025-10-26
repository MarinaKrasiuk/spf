package com.coherentsolutions.spftaskmanagementsystem.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Task {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @Builder.Default
    private Status status = Status.TODO;

    @NotNull
    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @Email
    @NotBlank
    private String assigneeEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
