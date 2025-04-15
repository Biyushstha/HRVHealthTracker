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

public class HRVViewModel extends AndroidViewModel {

    private static final String PREF_NAME = "health_prefs";
    private static final String KEY_LAST_DATE = "last_hrv_date";

    private final SharedPreferences prefs;
    private final MutableLiveData<Float> hrvLiveData = new MutableLiveData<>();
    private final String todayKey;

    public HRVViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences(PREF_NAME, Application.MODE_PRIVATE);

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        todayKey = "hrv_" + today;

        String lastDate = prefs.getString(KEY_LAST_DATE, "");
        if (!today.equals(lastDate)) {
            prefs.edit().putFloat(todayKey, -1f).putString(KEY_LAST_DATE, today).apply();
        }

        float stored = prefs.getFloat(todayKey, -1f);
        hrvLiveData.setValue(stored);
    }

    public LiveData<Float> getHrvValue() {
        return hrvLiveData;
    }

    public void saveHRV(float hrv) {
        prefs.edit().putFloat(todayKey, hrv).apply();
        hrvLiveData.setValue(hrv);
    }
}
