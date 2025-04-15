package com.example.wear.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private static final String PREF_NAME = "hydration_prefs";
    private static final String KEY_WATER_INTAKE = "water_intake";

    public static void saveWaterIntake(Context context, int ml) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_WATER_INTAKE, ml).apply();
    }

    public static int getWaterIntake(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_WATER_INTAKE, 0);
    }
}
