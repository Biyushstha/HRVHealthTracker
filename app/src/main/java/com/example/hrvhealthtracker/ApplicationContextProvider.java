package com.example.hrvhealthtracker;

import android.app.Application;
import android.content.Context;

public class ApplicationContextProvider extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ApplicationContextProvider.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
