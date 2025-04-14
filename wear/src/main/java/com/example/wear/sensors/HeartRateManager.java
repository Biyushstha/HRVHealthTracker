package com.example.wear.sensors;

import android.os.Handler;

public class HeartRateManager {

    private final HRVListener listener;
    private final Handler handler = new Handler();

    public HeartRateManager(HRVListener listener) {
        this.listener = listener;
    }

    public void startMockHRV() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int hrv = (int) (30 + Math.random() * 60);  // Random HRV value
                String stressLevel = classifyStress(hrv);
                listener.onStressUpdated(stressLevel, hrv);
                handler.postDelayed(this, 10000); // update every 10s
            }
        }, 1000);
    }

    public void stopMockHRV() {
        handler.removeCallbacksAndMessages(null);
    }

    private String classifyStress(int hrv) {
        if (hrv > 70) return "Relaxed";
        else if (hrv > 45) return "Moderate";
        else return "Stressed";
    }

    public interface HRVListener {
        void onStressUpdated(String status, int hrv);
    }
}
