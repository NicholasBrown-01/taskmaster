package com.nickbrown.taskmaster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskClass;
import com.amplifyframework.datastore.generated.model.Team;
import com.nickbrown.taskmaster.R;
import com.nickbrown.taskmaster.adapter.TaskClassAdapter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    public final String USERNAME_TAG = "Username";
    public static final String TASK_TITLE_EXTRA_TAG = "taskTitle";
    SharedPreferences preferences;

    List<TaskClass> taskItem = new ArrayList<>();

    TaskClassAdapter adapter;
;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this); //leaving this out causes your app to stop running and not launch.

        Button goToAddTaskActiviyPageButton = findViewById(R.id.MainActivityAddTaskButton);
        goToAddTaskActiviyPageButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });
        updateTaskListFromDatabase();
        setupRecyclerView();
        setupNavigationButton();

    }
    private void setupNavigationButton() {
        ImageView goToSettingsImage = findViewById(R.id.MainActivityImageToSettings);

        goToSettingsImage.setOnClickListener(v -> {
            Intent goToSettingsPage = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(goToSettingsPage);
        });
        Log.v(TAG, "Verbose log: ");
    }


    @Override
    protected void onResume() {
        super.onResume();

        setupUsernameTextHeader();
        updateTaskListFromDatabase();
        updateTotalTasksCount();
    }



    void setupUsernameTextHeader() {
        String userName = preferences.getString(USERNAME_TAG, "No Username");
        String addedText = userName + "'s tasks";
        ((TextView) findViewById(R.id.MainActivityUsernameTaskHeader)).setText(addedText);
    }

    void setupRecyclerView(){
        RecyclerView taskListRecyclerView = findViewById(R.id.MainActivityTaskRecyclerView);
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        taskListRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = spacingInPixels;
            }
        });


        adapter = new TaskClassAdapter(taskItem, this);
        taskListRecyclerView.setAdapter(adapter);


    }

    void updateTaskListFromDatabase() {
        Amplify.API.query(
                ModelQuery.list(TaskClass.class),
                success -> {
                    Log.i(TAG, "Read Tasks SUCCESSFULLY!!");
                    taskItem.clear();
                    for (TaskClass databaseTask : success.getData()) {
//                        String teamNumber = "Team1"; // Added semicolon
//                        if(databaseTask.getTeamTask() != null &&
//                        databaseTask.getTeamTask().getName().equals(teamNumber)) {
//                            taskItem.add(databaseTask);
//                        }
                    }
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                }, // Added closing parenthesis for the success lambda
                failure -> {
                    Log.i(TAG, "FAILED reading Tasks");
                }
        );
    }

    void updateTotalTasksCount() {
        TextView totalTasksTextView = findViewById(R.id.MainActivityTotalTaskTextView);
// TODO: Make Dynamo       int totalTasks = taskmasterDatabase.taskdao().getTotalTasksCount();
//        totalTasksTextView.setText("Total Tasks: " + totalTasks);
    }

}


