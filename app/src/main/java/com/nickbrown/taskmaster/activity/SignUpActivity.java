package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.nickbrown.taskmaster.R;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";

    Button submitButton;
    EditText emailEditText;
    EditText passwordEditText;
    EditText nicknameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nicknameEditText = findViewById(R.id.SignUpActivityNicknamePlainText);
        submitButton = findViewById(R.id.SignUpActivitySubmitButton);
        emailEditText = findViewById(R.id.SignUpActivityEmailText);
        passwordEditText = findViewById(R.id.SignUpActivityPasswordText);

        setupSubmitButton();
    }

        void setupSubmitButton(){
            submitButton.setOnClickListener(v -> {

                Amplify.Auth.signUp(emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        AuthSignUpOptions.builder()
                                .userAttribute(AuthUserAttributeKey.email(), emailEditText.getText().toString())
                                .userAttribute(AuthUserAttributeKey.nickname(), nicknameEditText.getText().toString())
                                .build(),
                        successResponse -> {
                            Log.i(TAG, "Sign Up SUCCESSFULL" + successResponse.toString());
                            Intent goToVerifyActivity = new Intent(SignUpActivity.this, VerifyActivity.class);
                            startActivity(goToVerifyActivity);
                        },
                        failureResponse -> Log.i(TAG, "Sign Up FAILED" + failureResponse)
                );
            });
        }
    }