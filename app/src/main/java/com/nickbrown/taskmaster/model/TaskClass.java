package com.nickbrown.taskmaster.model;

import androidx.annotation.NonNull;




public class TaskClass {
    private String title;
    private String body;
    private TasksENUM state;  // Using TasksENUM to represent state

    // Constructor for Room (which might require a default constructor)
    public TaskClass() {
    }

    // This constructor seems to be wrongly generated. I assume it's not used.
    // Remove or update it appropriately.
    @Deprecated
    public TaskClass(String toString, String toString1, Object fromString) {
    }

    // Primary constructor
    public TaskClass(String title, String body, TasksENUM state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    // Getter and Setter methods for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter methods for body
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // Getter and Setter methods for state
    public TasksENUM getState() {
        return state;
    }

    public void setState(TasksENUM state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaskClass{" +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", state=" + state +
                '}';
    }
}
