package com.example.wear.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepTracker implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor stepSensor;
    private final StepListener listener;

    public StepTracker(Context context, StepListener listener) {
        this.listener = listener;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    public void start() {
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int totalSteps = (int) event.values[0];
        listener.onStepChanged(totalSteps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public interface StepListener {
        void onStepChanged(int stepCount);
    }
}
