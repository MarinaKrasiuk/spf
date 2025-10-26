package com.coherentsolutions.spftaskmanagementsystem.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    TODO("To Do"),
    IN_PROGRESS("In progress"),
    DONE("Done");

    private final String value;

    public String getValue() {
        return value;
    }

    public static Status fromString(String input) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(input) || status.getValue().equalsIgnoreCase(input)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + input);
    }
}
