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
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskClass;
//import com.amplifyframework.datastore.generated.model.TasksENUM;
import com.amplifyframework.datastore.generated.model.TasksEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;
import com.nickbrown.taskmaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class AddTaskActivity extends AppCompatActivity {
    private final String TAG = "AddTaskActivity";

CompletableFuture<List<Team>> teamsFuture = null;

    Spinner teamSelectSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        teamSelectSpinner = findViewById(R.id.AddTaskActivityTeamSpinner);
        teamsFuture = new CompletableFuture<>();


        setupAddTaskActivitySpinner();
        setupAddTaskActivityTeamSpinner();
        setupAddTaskSaveButton();
    }

    void setupAddTaskActivitySpinner() {
        Spinner taskActivitySpinner = findViewById(R.id.AddTaskActivitySpinner);
        taskActivitySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TasksEnum.values()
        ));
    }

    void setupAddTaskActivityTeamSpinner() {
        Amplify.API.query(
                ModelQuery.list(Team.class), // Make sure to add .class here if Team is a class type
                success -> {
                    Log.i(TAG, "READ TEAMS SUCCESSFULLY");
                    ArrayList<String> teamNames = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for (Team team : success.getData()) {
                        teams.add(team); // Fixed line
                        teamNames.add(team.getName());
                    }
                    teamsFuture.complete(teams);

                    runOnUiThread(() -> {
                        teamSelectSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamNames));
                    });
                },
                failure -> {
                    teamsFuture.complete(null);
                    Log.i(TAG, "FAILED READ OF TEAMS!!!!!!!!!!!!!!!");
                }
        );
    }

    void setupAddTaskSaveButton() {
        Button addThisTaskButton = findViewById(R.id.ActivityAddThisTaskButton);
        Spinner taskActivitySpinner = findViewById(R.id.AddTaskActivitySpinner);

        addThisTaskButton.setOnClickListener(v -> {
            String selectedTeamString = teamSelectSpinner.getSelectedItem().toString();

            List<Team> teams = null;
            try {
                teams = teamsFuture.get();
            } catch (InterruptedException ie) {
                    Log.e(TAG, "INTERRUPTED EXCEPTION WHILE GETTING TEAMS");
                    Thread.currentThread().interrupt();
                } catch (ExecutionException ee){
            Log.e(TAG, "EXECUTION EXCEPTION WHILE GETTING TEAMS");
        }
            Team selectedTeam = teams.stream().filter(c -> c.getName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

            EditText AddTaskActivityTaskTitle = findViewById(R.id.AddTaskActivityTaskTitle);
            EditText AddTaskActivityAddTaskDescription = findViewById(R.id.AddTaskActivityAddTaskDescription);

            TaskClass taskToSave = TaskClass.builder()
                    .title(AddTaskActivityTaskTitle.getText().toString())
                    .body(AddTaskActivityAddTaskDescription.getText().toString())
                    .state((TasksEnum) taskActivitySpinner.getSelectedItem())
                    .teamTask(selectedTeam)
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
