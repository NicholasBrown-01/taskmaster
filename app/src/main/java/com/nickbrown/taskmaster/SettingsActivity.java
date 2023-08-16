package com.nickbrown.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    public static final String USERNAME_TAG = "Username";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setupUsernameEditText();
        setupSaveButton();
    }

    void setupUsernameEditText() {
        String Username = preferences.getString(USERNAME_TAG, null);
        ((EditText)findViewById(R.id.SettingActivityUsernameInputBox)).setText(Username);
    }

    void setupSaveButton() {
        ((Button)findViewById(R.id.SettingsActivityUsernameSaveButton)).setOnClickListener(v -> {
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            EditText usernameEditText = (EditText) findViewById(R.id.SettingActivityUsernameInputBox);
            String usernameString = usernameEditText.getText().toString();

            preferencesEditor.putString(USERNAME_TAG, usernameString);
            preferencesEditor.apply();
            Toast.makeText(SettingsActivity.this, "Username Saved!", Toast.LENGTH_SHORT).show();
        });
    }
}