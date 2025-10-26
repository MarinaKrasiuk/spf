package com.coherentsolutions.spftaskmanagementsystem.models;

import lombok.AllArgsConstructor;

/**
 * Represents priority levels for tasks in the Task Management System.
 */
@AllArgsConstructor
public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    CRITICAL("Critical");


    private final String value;
    /**
     * Parse a string to the corresponding Priority.
     * Accepts both name() and value(), case-insensitive.
     *
     * @param value the string to parse
     * @return the corresponding Priority
     * @throws IllegalArgumentException if no match is found
     */

    public String getValue() {
        return value;
    }
    public static Priority fromString(String value) {
        for (Priority p : Priority.values()) {
            if (p.value.equalsIgnoreCase(value) || p.name().equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown priority: " + value);
    }
}
