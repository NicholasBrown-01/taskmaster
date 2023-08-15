package com.nickbrown.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button addThisTaskButton = findViewById(R.id.ActivityAddThisTaskButton);
        addThisTaskButton.setOnClickListener(v -> {
            System.out.println("Submitted!");


        });

    }
}