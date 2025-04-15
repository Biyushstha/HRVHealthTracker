package com.example.wear.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeartRateManager implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor heartSensor;
    private final List<Float> hrvSamples = new ArrayList<>();
    private final HRVCallback callback;

    private final boolean TEST_MODE = true;

    private final Handler testHandler = new Handler(Looper.getMainLooper());
    private final Runnable testRunnable = new Runnable() {
        @Override
        public void run() {
            simulateHRVForTesting();
            testHandler.postDelayed(this, 2000); // every 2s
        }
    };

    // ✅ Store last HRV to avoid flickering
    private Float lastHRV = null;

    public interface HRVCallback {
        void onNewHRVValue(float hrv);
    }

    public HeartRateManager(Context context, HRVCallback callback) {
        this.callback = callback;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        heartSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    public void start() {
        if (TEST_MODE) {
            testHandler.post(testRunnable);
        } else if (heartSensor != null) {
            sensorManager.registerListener(this, heartSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        if (TEST_MODE) {
            testHandler.removeCallbacks(testRunnable);
        } else {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!TEST_MODE && event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            float bpm = event.values[0];
            if (bpm > 0) {
                float ibi = 60000f / bpm;
                hrvSamples.add(ibi);

                if (hrvSamples.size() >= 20) {
                    float sdnn = calculateSDNN(hrvSamples);
                    lastHRV = sdnn;
                    callback.onNewHRVValue(sdnn);
                    hrvSamples.clear();
                } else if (lastHRV == null) {
                    callback.onNewHRVValue(-1); // only if no HRV yet
                }
            }
        }
    }

    private void simulateHRVForTesting() {
        Random rand = new Random();
        float fakeBpm = 60 + rand.nextInt(100); // 60–160 bpm
        float simulatedIBI = 60000f / fakeBpm;

        hrvSamples.add(simulatedIBI);

        if (hrvSamples.size() >= 20) {
            float sdnn = calculateSDNN(hrvSamples);
            lastHRV = sdnn;
            callback.onNewHRVValue(sdnn);
            hrvSamples.clear();
        } else if (lastHRV == null) {
            callback.onNewHRVValue(-1);
        }
    }

    private float calculateSDNN(List<Float> ibiList) {
        float sum = 0;
        for (float ibi : ibiList) sum += ibi;
        float avg = sum / ibiList.size();

        float variance = 0;
        for (float ibi : ibiList) variance += Math.pow(ibi - avg, 2);
        return (float) Math.sqrt(variance / ibiList.size());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
