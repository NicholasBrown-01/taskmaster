package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.nickbrown.taskmaster.R;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginAdtivity";

    Button submitButton;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submitButton = findViewById(R.id.LoginActivitySubmitButton);
        emailEditText = findViewById(R.id.LoginActivityEmailEditText);
        passwordEditText = findViewById(R.id.LoginActivityPasswordEditText);

        setupSubmitButton();
    }


    void setupSubmitButton() {
        submitButton.setOnClickListener(v -> {
            Amplify.Auth.signIn(emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    success -> {
                        Log.i(TAG, "Login SUCCEEDED" + success.toString());
                        Intent goToMainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMainActivityIntent);
                    },
                    failure -> Log.i(TAG, "Login FAILED" + failure.toString())
            );
        });
    }
}



