package com.example.wear.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wear.R;
import com.example.wear.sensors.HeartRateManager;
import com.example.wear.sensors.StepTracker;

public class MainActivity extends Activity {

    private StepTracker stepTracker;
    private HeartRateManager heartRateManager;

    private TextView stepCountView;
    private TextView stressLabel;
    private TextView emojiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind UI elements
        stepCountView = findViewById(R.id.step_count);
        stressLabel = findViewById(R.id.stress_status);
        emojiView = findViewById(R.id.emoji_stress);
        LinearLayout waterCard = findViewById(R.id.water_card);

        // Handle water card click to open WaterActivity
        waterCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WaterActivity.class);
            startActivity(intent);
        });

        // Initialize and start Step Tracker
        stepTracker = new StepTracker(this, stepCount -> {
            runOnUiThread(() -> stepCountView.setText(String.valueOf(stepCount)));
        });

        // Initialize and start Heart Rate mock HRV
        heartRateManager = new HeartRateManager((status, hrv) -> {
            runOnUiThread(() -> {
                stressLabel.setText(status);
                switch (status) {
                    case "Relaxed":
                        emojiView.setText("😌");
                        break;
                    case "Moderate":
                        emojiView.setText("😐");
                        break;
                    case "Stressed":
                        emojiView.setText("😟");
                        break;
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        stepTracker.start();
        heartRateManager.startMockHRV();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stepTracker.stop();
        heartRateManager.stopMockHRV();
    }
}
