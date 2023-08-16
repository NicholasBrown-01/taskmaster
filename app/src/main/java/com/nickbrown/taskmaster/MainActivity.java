package com.nickbrown.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    public final String USERNAME_TAG = "Username";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this); //leaving this out causes your app to stop running and not launch.

//        Button addTaskButton = findViewById(R.id.MainActivityAddTaskButton);

        Button laundryButton = findViewById(R.id.MainActivityLaundryButton);
        Button trashButton = findViewById(R.id.MainActivityTrashButton);
        Button dishesButton = findViewById(R.id.MainActivityDishesButton);

        setupNavigationButton(laundryButton);
        setupNavigationButton(trashButton);
        setupNavigationButton(dishesButton);
    }
    private void setupNavigationButton(Button button) {
        button.setOnClickListener(v -> {
            Intent goToAllTasksIntent = new Intent(MainActivity.this, AllTasksActivity.class);

            int buttonId = button.getId();
            if (buttonId == R.id.MainActivityLaundryButton) {
                goToAllTasksIntent.putExtra("taskType", "Laundry");
            } else if (buttonId == R.id.MainActivityTrashButton) {
                goToAllTasksIntent.putExtra("taskType", "Trash");
            } else if (buttonId == R.id.MainActivityDishesButton) {
                goToAllTasksIntent.putExtra("taskType", "Dishes");
            }

            startActivity(goToAllTasksIntent);
        });


        Button allTasksButton = findViewById(R.id.MainActivityTaskDetailsButton);
        ImageView goToSettingsImage = findViewById((R.id.MainActivityImageToSettings));

//        addTaskButton.setOnClickListener(v -> {
//            Intent goToAddTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
//            startActivity(goToAddTaskIntent);
//        });

        allTasksButton.setOnClickListener(v -> {
            Intent goToAllTasksIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(goToAllTasksIntent);

        });

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
}


