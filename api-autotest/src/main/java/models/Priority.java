package models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    CRITICAL("Critical");

    private final String value;
}
