package com.nickbrown.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AllTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        String taskType = getIntent().getStringExtra("taskType");
        if (taskType != null && !taskType.isEmpty()) {
            TextView headerTextView = findViewById(R.id.textView3);
            headerTextView.setText(taskType + " Tasks");
        }
    }
}