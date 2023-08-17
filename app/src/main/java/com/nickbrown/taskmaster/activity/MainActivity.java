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
import android.widget.ImageView;
import android.widget.TextView;

import com.nickbrown.taskmaster.R;
import com.nickbrown.taskmaster.adapter.TaskClassAdapter;
import com.nickbrown.taskmaster.model.TaskClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    public final String USERNAME_TAG = "Username";
    public static final String TASK_TITLE_EXTRA_TAG = "taskTitle";
    SharedPreferences preferences;

    List<TaskClass> taskItem = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this); //leaving this out causes your app to stop running and not launch.


        createTasks();
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


        TaskClassAdapter adapter = new TaskClassAdapter(taskItem, this);
        taskListRecyclerView.setAdapter(adapter);
    }


    void createTasks() {
        taskItem.add(new TaskClass("Laundry", "Wash and fold the clothes", TaskClass.TaskState.ASSIGNED));
        taskItem.add(new TaskClass("Trash", "Take out the garbage", TaskClass.TaskState.IN_PROGRESS));
        taskItem.add(new TaskClass("Dishes", "Clean and put away the dishes", TaskClass.TaskState.COMPLETE));
        taskItem.add(new TaskClass("Vacuum", "Vacuum the living room and bedrooms", TaskClass.TaskState.NEW));
        taskItem.add(new TaskClass("Dusting", "Dust everything", TaskClass.TaskState.ASSIGNED));
        taskItem.add(new TaskClass("Cooking", "Prepare dinner for tonight", TaskClass.TaskState.IN_PROGRESS));
        taskItem.add(new TaskClass("Gardening", "Water the plants and trim the bushes", TaskClass.TaskState.COMPLETE));
        taskItem.add(new TaskClass("Car", "Wash the car and check the oil", TaskClass.TaskState.ASSIGNED));
        taskItem.add(new TaskClass("Emails", "Clear out emails", TaskClass.TaskState.IN_PROGRESS));
        taskItem.add(new TaskClass("Bills", "Pay the monthly bills", TaskClass.TaskState.NEW));
        taskItem.add(new TaskClass("Exercise", "Go for a 30-minute jog", TaskClass.TaskState.ASSIGNED));
        taskItem.add(new TaskClass("Study", "Read two chapters", TaskClass.TaskState.IN_PROGRESS));
        taskItem.add(new TaskClass("Dog", "Walk the dog around the park", TaskClass.TaskState.COMPLETE));
        taskItem.add(new TaskClass("Repair", "Fix the leaky faucet in the bathroom", TaskClass.TaskState.NEW));
    }
}


