package com.nickbrown.taskmaster.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskClass;
//import com.amplifyframework.datastore.generated.model.TasksENUM;
import com.amplifyframework.datastore.generated.model.TasksEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.snackbar.Snackbar;
import com.nickbrown.taskmaster.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class AddTaskActivity extends AppCompatActivity {
    private final String TAG = "AddTaskActivity";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;

    private final MediaPlayer mp = new MediaPlayer();

CompletableFuture<List<Team>> teamsFuture = null;

    private String s3ImageKey = ""; // holds the image S3 key if one currently exists in this activity, or the empty String if there is no image picked in this activity currently

    Spinner teamSelectSpinner;
    ImageView taskImageView;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        teamSelectSpinner = findViewById(R.id.AddTaskActivityTeamSpinner);
        teamsFuture = new CompletableFuture<>();
        taskImageView = findViewById(R.id.AddTaskActivityTaskImageView);
        activityResultLauncher = getImagePickingActivityResultLauncher();



        setupAddTaskActivityTaskTitle();
        setupAddTaskActivitySpinner();
        setupAddTaskActivityTeamSpinner();
        setupAddTaskSaveButton();
        setupTaskImageView();
    }

    void setupAddTaskActivityTaskTitle() {
        EditText AddTaskActivityTaskTitle = findViewById(R.id.AddTaskActivityTaskTitle);

        // Set up a click listener for the EditText
        AddTaskActivityTaskTitle.setOnClickListener(v -> {
            String taskTitle = AddTaskActivityTaskTitle.getText().toString();
            Amplify.Predictions.convertTextToSpeech(
                    taskTitle,
                    result -> playAudio(result.getAudioData()),
                    error -> Log.e(TAG, "Audio conversion of task title text failed", error)
            );
        });
    }

    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;

            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            Log.i(TAG, "audio file finished reading");

            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();

            Log.i(TAG, "Audio played successfully");
        } catch (IOException ioe) {
            Log.e(TAG, "Error writing audio file", ioe);
        }
    }

    void setupTaskImageView() {
        taskImageView.setOnClickListener(v -> {
            launchImageSelectionIntent();
        });
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
//        getUserLastLocation();
        getUserCurrentLocation();
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
                    .taskImageS3Key(s3ImageKey)
//                    .latitude(stringReturn[0])
//                    .long(stringReturn[1])
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(taskToSave),
                    successResponse -> {
                        Log.i(TAG, "Made Task SUCCESSFULLY" + successResponse);

                        // Navigate to AllTasksActivity and pass the S3 key
                        Intent allTasksActivityIntent = new Intent(AddTaskActivity.this, AllTasksActivity.class);
                        allTasksActivityIntent.putExtra("S3ImageKey", s3ImageKey);
                        startActivity(allTasksActivityIntent);

                    },
                    failureResponse -> Log.i(TAG, "Made Task FAILED" + failureResponse)
            );

            Snackbar.make(findViewById(R.id.addTaskSubmittedText), "Task Saved!", Snackbar.LENGTH_SHORT).show();
        });
    }

    void startTrackingUserLocation() {
        LocationRequest locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000)
                .build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                try {
                    String address = geocoder.getFromLocation(
                                    locationResult.getLastLocation().getLatitude(),
                                    locationResult.getLastLocation().getLongitude(),
                                    1) // give us only the best guess
                            .get(0) // grab the best guess from the list of Addresses
                            .getAddressLine(0); // get the first line String from the Address object

                    Log.i(TAG, "Repeating current location is: " + address);
                } catch (IOException ioe) {
                    Log.e(TAG, "Could not get subscribed location: " + ioe.getMessage(), ioe);
                }
            }
        };

        // checking for permissions before accessing location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    void getUserLastLocation() {
        // TODO: Class39 Step 4-2: Ensure you have permission to access location before doing so
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //TODO: Class39 Step 4-1 Implementation: Grab the current user's location when they select save
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) {
                Log.e(TAG, "Location callback was null");
            }
            String currentLatitude = Double.toString(location.getLatitude());
            String currentLongitude = Double.toString(location.getLongitude());
            Log.i(TAG, "User's last latitude: " + currentLatitude);
            Log.i(TAG, "User's last longitude: " + currentLongitude);
            // For Lab39: Save these vars to be included as part of your Task object
        });
    }

    void getUserCurrentLocation() {
        fusedLocationProviderClient.flushLocations(); // <-- call this is you're not seeing location changes

        // Make sure you have permissions before accessing a user's location!
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        // grab the user's current location
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        }).addOnSuccessListener(location -> {
            if (location == null) {
                Log.e(TAG, "Location callback was null");
            }
            String currentLatitude = Double.toString(location.getLatitude());
            String currentLongitude = Double.toString(location.getLongitude());

            // Store location in Shared Preferences
            SharedPreferences sharedPreferences = getSharedPreferences("userLocation", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude", currentLatitude);
            editor.putString("longitude", currentLongitude);
            editor.apply();

            Log.i(TAG, "User's current latitude: " + currentLatitude);
            Log.i(TAG, "User's current longitude: " + currentLongitude);
        });
    }

    void launchImageSelectionIntent() {
        // Part 1: Launch Android's activity to pick an image file
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT); // one of several file picking activities built into Android
        imageFilePickingIntent.setType("*/*"); // only allow one kind or category of file; if you don't have this, you get a very cryptic error about "No activity found to handle Intent"
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"}); // only pick JPEG and PNG images

        // Launch Android's built-in file picking activity using our newly created ActivityResultLauncher below
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        // Part 2: Android calls your callback with the picked image
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            Uri pickedImageFileUri = result.getData().getData();
                            try {
                                InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                            } catch (FileNotFoundException fnfe) {
                                Log.e(TAG, "Could not get file form file picker! " + fnfe.getMessage(), fnfe);
                            }
                        }
                );

        return imagePickingActivityResultLauncher;
    }

    void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri) {
        // Part 3: Use our InputStream to upload file to S3

        Amplify.Storage.uploadInputStream(
                pickedImageFilename, // S3 key
                pickedImageInputStream,
                success -> {
                    // confirm that we succeeded our upload and grab the key
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = success.getKey(); // non-empty s3ImageKey indicates an image was picked in this activity
                    // TODO: update our saveProduct to include the s3 key

                    // grabbing a new input stream since we used the original for uploading to S3
                    InputStream pickedImageInputStreamCopy = null; // need to make a copy because InputStreams cannot be reused!
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    } catch (FileNotFoundException fnfe) {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }

                    // set te imageView with the selected image
                    taskImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> {
                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
                }
        );
    }

    // Taken from https://stackoverflow.com/a/25005243/16889809
    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
