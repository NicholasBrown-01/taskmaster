package com.nickbrown.taskmaster.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.nickbrown.taskmaster.R;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {
    public static final String USERNAME_TAG = "Username";
    public static final String SELECTED_TEAM_TAG = "selectedTeam";
    SharedPreferences preferences;

    Button loginButton;
    Button signupButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        loginButton = findViewById(R.id.SettingActivityLoginButton);
        signupButton = findViewById(R.id.SettingsActivitySignupButton);
        logoutButton = findViewById(R.id.SettingsActivityLogoutButton);

        setupUsernameEditText();
        setupTeamSpinner();
        setupSaveButton();
        setupLoginButton();
        setupSignupButton();
        setupLogoutButton();
    }

    void setupUsernameEditText() {
        String Username = preferences.getString(USERNAME_TAG, null);
        ((EditText) findViewById(R.id.SettingActivityUsernameInputBox)).setText(Username);
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

    void setupLoginButton() {
        loginButton.setOnClickListener(v -> {
            Intent goToLoginActivityIntent = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(goToLoginActivityIntent);
        });

    }

    void setupSignupButton() {
        signupButton.setOnClickListener(v -> {
            Intent goToSignUpActivityIntent = new Intent(SettingsActivity.this, SignUpActivity.class);
            startActivity(goToSignUpActivityIntent);
        });
    }

    void setupLogoutButton() {
        logoutButton.setOnClickListener(v -> {
            AuthSignOutOptions signOutOptions = AuthSignOutOptions.builder()
                    .globalSignOut(true)
                    .build();

            Amplify.Auth.signOut(signOutOptions,
                    signOutResult -> {
                        if (signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                            Log.i(TAG, "Global sign out successful!!!");
                        } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                            Log.i(TAG, "Partial sign out successful!!!");
                        } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                            Log.i(TAG, "Logout Failed: " + signOutResult.toString());
                        }
                    }
            );
        });
    }
}
