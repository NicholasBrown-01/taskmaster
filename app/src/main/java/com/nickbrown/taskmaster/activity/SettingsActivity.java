package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nickbrown.taskmaster.R;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {
    public static final String USERNAME_TAG = "Username";
    public static final String SELECTED_TEAM_TAG = "selectedTeam";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setupUsernameEditText(); // Ensure this method is called to populate the EditText with the saved username
        setupTeamSpinner();
        setupSaveButton();
    }

    void setupUsernameEditText() {
        String Username = preferences.getString(USERNAME_TAG, null);
        ((EditText)findViewById(R.id.SettingActivityUsernameInputBox)).setText(Username);
    }

    void setupTeamSpinner() {
        Spinner teamSpinner = findViewById(R.id.SelectTeamSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList("Team 1", "Team 2", "Team 3"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(adapter);

        String selectedTeam = preferences.getString(SELECTED_TEAM_TAG, "Team 1");
        teamSpinner.setSelection(adapter.getPosition(selectedTeam));
    }

    void setupSaveButton() {
        findViewById(R.id.SettingsActivityUsernameSaveButton).setOnClickListener(v -> {
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            EditText usernameEditText = findViewById(R.id.SettingActivityUsernameInputBox);
            String usernameString = usernameEditText.getText().toString();

            Spinner teamSpinner = findViewById(R.id.SelectTeamSpinner);
            String selectedTeam = teamSpinner.getSelectedItem().toString();

            preferencesEditor.putString(USERNAME_TAG, usernameString);
            preferencesEditor.putString(SELECTED_TEAM_TAG, selectedTeam);
            preferencesEditor.apply();

            Toast.makeText(SettingsActivity.this, "Settings Saved!", Toast.LENGTH_SHORT).show();
        });
    }
}