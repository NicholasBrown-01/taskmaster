package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nickbrown.taskmaster.R;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button addThisTaskButton = findViewById(R.id.ActivityAddThisTaskButton);
        TextView submittedTextLabel = findViewById(R.id.addTaskSubmittedText);

        addThisTaskButton.setOnClickListener(v -> {
            System.out.println("Submitted!");
            submittedTextLabel.setVisibility(View.VISIBLE);

        });

    }
}