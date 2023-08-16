package com.nickbrown.taskmaster;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskClass {
    // Fields
    private String title;
    private String body;
    private TaskState state;

    // Enum for TaskState
    public enum TaskState {
        NEW, ASSIGNED, IN_PROGRESS, COMPLETE
    }

    // Constructor


    public TaskClass() {
    }

    public TaskClass(String title, String body, TaskState state) {
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
    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaskClass{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", state=" + state +
                '}';
    }
}
