package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.nickbrown.taskmaster.R;

import java.io.File;

public class AllTasksActivity extends AppCompatActivity {
    ImageView allTaskActivityS3ImageView;
    String s3ImageKey; // Declare this variable to store the S3 key
    Intent callingIntent;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        // Fetch Location from Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("userLocation", MODE_PRIVATE);
        String latitude = sharedPreferences.getString("latitude", "Not available");
        String longitude = sharedPreferences.getString("longitude", "Not available");
        // Display Location in TextView
        TextView locationTextView = findViewById(R.id.AllTasksActivityLocationTextView);
        locationTextView.setText("Lat: " + latitude + ", Long: " + longitude);

        callingIntent = getIntent();

        allTaskActivityS3ImageView = findViewById(R.id.AllTaskActivityS3ImageView);

        // Fetch the S3 key from the Intent
        s3ImageKey = getIntent().getStringExtra("S3ImageKey");

        // Call this method to populate the ImageView
        populateImageView();

        String taskType = getIntent().getStringExtra(MainActivity.TASK_TITLE_EXTRA_TAG);
        String taskDescription = getIntent().getStringExtra("TASK_DESCRIPTION");
        String taskCategory = getIntent().getStringExtra("TASK_CATEGORY");

        if (taskType != null && !taskType.isEmpty()) {
            TextView headerTextView = findViewById(R.id.textView3); // Assuming this is the header
            headerTextView.setText(taskType + " Tasks");

            // Display description if you have a designated TextView for it
            if (taskDescription != null && !taskDescription.isEmpty()) {
                TextView descriptionTextView = findViewById(R.id.AllTasksDescriptionTextView); // Replace with your actual ID
                descriptionTextView.setText(taskDescription);
            }

            // Display category if you have a designated TextView for it
            if (taskCategory != null && !taskCategory.isEmpty()) {
                TextView categoryTextView = findViewById(R.id.AllTasksStatusTextView); // Replace with your actual ID
                categoryTextView.setText(taskCategory);
            }
        }
    }

    // This method is now a separate method, outside of onCreate()
    void populateImageView() {
        // Check if s3ImageKey is null before proceeding
        if (s3ImageKey != null) {
            // Check if s3ImageKey includes a folder name and truncate it
            int cut = s3ImageKey.lastIndexOf('/');
            if (cut != -1) {
                s3ImageKey = s3ImageKey.substring(cut + 1);
            }

            // Your existing download code
            if (!s3ImageKey.isEmpty()) {
                Amplify.Storage.downloadFile(
                        s3ImageKey,
                        new File(getApplicationContext().getFilesDir(), s3ImageKey),
                        success -> {
                            Bitmap myBitmap = BitmapFactory.decodeFile(success.getFile().getAbsolutePath());
                            allTaskActivityS3ImageView.setImageBitmap(myBitmap);
                        },
                        failure -> {
                            Log.e("AllTasksActivity", "Unable to get image from S3 for the S3 key: " + s3ImageKey + " for reason: " + failure.getMessage());
                        }
                );
            }
        } else {
            Log.e("AllTasksActivity", "s3ImageKey is null");
        }
    }
}
