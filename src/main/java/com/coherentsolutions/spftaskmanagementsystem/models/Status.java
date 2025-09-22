package com.coherentsolutions.spftaskmanagementsystem.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    TODO("To Do"),
    IN_PROGRESS("In progress"),
    DONE("Done");

  private final String value;
}
