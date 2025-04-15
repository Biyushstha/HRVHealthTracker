package com.example.wear.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WaterViewModel extends AndroidViewModel {

    private static final String PREF_NAME = "health_prefs";
    private static final String KEY_LAST_DATE = "last_water_date";

    private final SharedPreferences prefs;
    private final MutableLiveData<Integer> waterLiveData = new MutableLiveData<>();
    private final String todayKey;

    public WaterViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences(PREF_NAME, Application.MODE_PRIVATE);

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        todayKey = "water_" + today;

        String lastDate = prefs.getString(KEY_LAST_DATE, "");
        if (!today.equals(lastDate)) {
            prefs.edit().putInt(todayKey, 0).putString(KEY_LAST_DATE, today).apply();
        }

        int stored = prefs.getInt(todayKey, 0);
        waterLiveData.setValue(stored);
    }

    public LiveData<Integer> getWaterIntake() {
        return waterLiveData;
    }

    public void addWater(int amount) {
        int current = waterLiveData.getValue() != null ? waterLiveData.getValue() : 0;
        int updated = current + amount;
        prefs.edit().putInt(todayKey, updated).apply();
        waterLiveData.setValue(updated);
    }

    public void resetWater() {
        prefs.edit().putInt(todayKey, 0).apply();
        waterLiveData.setValue(0);
    }

    // ✅ Public method to refresh from SharedPreferences
    public void refreshWater() {
        int updated = prefs.getInt(todayKey, 0);
        waterLiveData.setValue(updated);
    }
}
