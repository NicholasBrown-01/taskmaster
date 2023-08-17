package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.nickbrown.taskmaster.R;
import com.nickbrown.taskmaster.database.TaskmasterDatabase;
import com.nickbrown.taskmaster.model.TaskClass;
import com.nickbrown.taskmaster.model.TasksENUM;

public class AddTaskActivity extends AppCompatActivity {
    TaskmasterDatabase taskmasterDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskmasterDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskmasterDatabase.class,
                        MainActivity.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        setupAddTaskActivitySpinner();
        setupAddTaskSaveButton();
    }

    void setupAddTaskActivitySpinner() {
        Spinner taskActivitySpinner = findViewById(R.id.AddTaskActivitySpinner);
        taskActivitySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TasksENUM.values()
        ));
    }

    void setupAddTaskSaveButton() {
        Button addThisTaskButton = findViewById(R.id.ActivityAddThisTaskButton);
        Spinner taskActivitySpinner = findViewById(R.id.AddTaskActivitySpinner);

        addThisTaskButton.setOnClickListener(v -> {
            String title = ((EditText) findViewById(R.id.AddTaskActivityTaskTitle)).getText().toString();
            String body = ((EditText) findViewById(R.id.AddTaskActivityAddTaskDescription)).getText().toString();
            TasksENUM state = TasksENUM.valueOf(taskActivitySpinner.getSelectedItem().toString());

            TaskClass taskToSave = new TaskClass(title, body, state);

            taskmasterDatabase.taskdao().insertATask(taskToSave);
            Snackbar.make(findViewById(R.id.addTaskSubmittedText), "Task Saved!", Snackbar.LENGTH_SHORT).show();

            // Update the total task count after saving the task
            updateTotalTasksCount();
        });
    }

    private void updateTotalTasksCount() {
        TextView totalTasksTextView = findViewById(R.id.textView7);
        int totalTasks = taskmasterDatabase.taskdao().getTotalTasksCount();
        totalTasksTextView.setText("Total Tasks: " + totalTasks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTotalTasksCount();
    }
}
