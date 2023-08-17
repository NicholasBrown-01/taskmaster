package com.nickbrown.taskmaster.model;

public enum TasksENUM {
    NEW("NEW"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete");

    TasksENUM() {
    }

    TasksENUM(String s) {
    }

    // Moved TaskState enum here
    public enum TaskState {
        NEW, ASSIGNED, IN_PROGRESS, COMPLETE
    }
}
