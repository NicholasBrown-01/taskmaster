package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nickbrown.taskmaster.R;

public class AllTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        String taskType = getIntent().getStringExtra(MainActivity.TASK_TITLE_EXTRA_TAG);
        String taskDescription = getIntent().getStringExtra("TASK_DESCRIPTION");
        String taskCategory = getIntent().getStringExtra("TASK_CATEGORY");

        if (taskType != null && !taskType.isEmpty()) {
            TextView headerTextView = findViewById(R.id.textView3); // Assuming this is the header
            headerTextView.setText(taskType + " Tasks");

            // Display description if you have a designated TextView for it
            if(taskDescription != null && !taskDescription.isEmpty()) {
                TextView descriptionTextView = findViewById(R.id.AllTasksDescriptionTextView); // Replace with your actual ID
                descriptionTextView.setText(taskDescription);
            }

            // Display category if you have a designated TextView for it
            if(taskCategory != null && !taskCategory.isEmpty()) {
                TextView categoryTextView = findViewById(R.id.AllTasksStatusTextView); // Replace with your actual ID
                categoryTextView.setText(taskCategory);
            }
        }
    }
}