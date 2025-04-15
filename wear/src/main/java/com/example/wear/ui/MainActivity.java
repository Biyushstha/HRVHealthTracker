package com.example.wear.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.wear.R;
import com.example.wear.sensors.HeartRateManager;
import com.example.wear.sensors.StepTracker;
import com.example.wear.viewmodel.HRVViewModel;
import com.example.wear.viewmodel.WaterViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends ComponentActivity {

    private HeartRateManager heartRateManager;
    private StepTracker stepTracker;

    private TextView emojiView, labelView, statusView;
    private TextView stepCountView, waterView;
    private LinearLayout waterCard;

    private WaterViewModel waterViewModel;
    private HRVViewModel hrvViewModel;

    private static final int GOAL = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emojiView = findViewById(R.id.emoji_stress);
        labelView = findViewById(R.id.stress_label);
        statusView = findViewById(R.id.stress_status);
        stepCountView = findViewById(R.id.step_count);
        waterView = findViewById(R.id.water_amount);
        waterCard = findViewById(R.id.water_card);

        // HRV ViewModel
        hrvViewModel = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(HRVViewModel.class);

        hrvViewModel.getHrvValue().observe(this, hrv -> {
            if (hrv == null || hrv < 0) {
                emojiView.setText("🤔");
                labelView.setText("You are");
                statusView.setText("Insufficient Data");
            } else if (hrv < 20) {
                emojiView.setText("😰");
                labelView.setText("You are");
                statusView.setText("Stressed");
            } else if (hrv < 40) {
                emojiView.setText("😐");
                labelView.setText("You are");
                statusView.setText("Okay");
            } else {
                emojiView.setText("😌");
                labelView.setText("You are");
                statusView.setText("Relaxed");
            }
        });

        // Water ViewModel
        waterViewModel = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(WaterViewModel.class);

        waterViewModel.getWaterIntake().observe(this, ml -> {
            waterView.setText(ml + " ml / " + GOAL + " ml");
        });

        // Heart Rate Listener
        heartRateManager = new HeartRateManager(this, hrv -> {
            if (hrv >= 0) {
                hrvViewModel.saveHRV(hrv);
            }
        });

        // Step Tracker
        stepTracker = new StepTracker(this, steps -> runOnUiThread(() -> {
            if (steps < 0) {
                stepCountView.setText("-");
            } else {
                stepCountView.setText(String.valueOf(steps));
            }
        }));

        waterCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WaterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        heartRateManager.start();
        stepTracker.start();

        //  Force refresh LiveData manually from SharedPreferences
        int updated = getSharedPreferences("health_prefs", MODE_PRIVATE)
                .getInt("water_" + getToday(), 0);

        waterViewModel.refreshWater();

    }

    @Override
    protected void onPause() {
        super.onPause();
        heartRateManager.stop();
        stepTracker.stop();
    }

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}
