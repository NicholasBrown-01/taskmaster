package com.nickbrown.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.nickbrown.taskmaster.R;

public class VerifyActivity extends AppCompatActivity {
    private static String TAG = "VerifyActivity";

    Button submitButton;
    EditText emailEditText;
    EditText verificationCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        submitButton = findViewById(R.id.VerifyActivitySubmitButton);
        emailEditText = findViewById(R.id.VerifyActivityEmailTextView);
        verificationCodeEditText = findViewById(R.id.VerifyActivityVerifyNumberTextView);

        setupSubmitButton();
    }


    void setupSubmitButton() {
        submitButton.setOnClickListener(v -> {
            Amplify.Auth.confirmSignUp(
                    emailEditText.getText().toString(),
                    verificationCodeEditText.getText().toString(),
                    success -> {
                        Log.i(TAG, "Verfication SUCCESS: " + success.toString());
                        Intent goToLoginIntent = new Intent(VerifyActivity.this, LoginActivity.class);
                        startActivity(goToLoginIntent);
                    },
                    failure -> {
                        Log.i(TAG, "Verification FAILED: " + failure.toString());
                    }
            );
        });
    }
}





