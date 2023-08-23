package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskClass;
import com.amplifyframework.datastore.generated.model.TasksENUM;
import com.google.android.material.snackbar.Snackbar;
import com.nickbrown.taskmaster.R;



public class AddTaskActivity extends AppCompatActivity {
    private final String TAG = "AddTaskActivity";
//    TaskmasterDatabase taskmasterDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

//        taskmasterDatabase = Room.databaseBuilder(
//                        getApplicationContext(),
//                        TaskmasterDatabase.class,
//                        MainActivity.DATABASE_NAME)
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build();

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

            EditText AddTaskActivityTaskTitle = findViewById(R.id.AddTaskActivityTaskTitle);
            EditText AddTaskActivityAddTaskDescription = findViewById(R.id.AddTaskActivityAddTaskDescription);

            TaskClass taskToSave = TaskClass.builder()
                    .title(AddTaskActivityTaskTitle.getText().toString())
                    .body(AddTaskActivityAddTaskDescription.getText().toString())
                    .state((TasksENUM) taskActivitySpinner.getSelectedItem())
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(taskToSave),//This is your request to cloud
                    successResponse -> Log.i(TAG, "Made Task SUCCESSFULLY" + successResponse),
                    failureResponse -> Log.i(TAG, "Made Task FAILED" + failureResponse)
            );

            Snackbar.make(findViewById(R.id.addTaskSubmittedText), "Task Saved!", Snackbar.LENGTH_SHORT).show();
            // Update the total task count after saving the task
            updateTotalTasksCount();
        });
    }

    private void updateTotalTasksCount() {
        TextView totalTasksTextView = findViewById(R.id.textView7);
//    TODO: Dynamo   int totalTasks = taskmasterDatabase.taskdao().getTotalTasksCount();
//        totalTasksTextView.setText("Total Tasks: " + totalTasks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTotalTasksCount();
    }
}
