package com.nickbrown.taskmaster;


import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

public class TaskmasterAmplifyApplication extends Application {
    public static final String TAG = "LOOK AT TaskmasterApplication.java";

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        } catch(AmplifyException amplifyException) {
            Log.e(TAG, "Error initializing AMPLIFY: " + amplifyException.getMessage(), amplifyException);
        }
    }
}
