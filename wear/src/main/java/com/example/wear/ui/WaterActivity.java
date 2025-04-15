package com.example.wear.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.wear.R;
import com.example.wear.sync.WaterSyncSender;
import com.example.wear.viewmodel.WaterViewModel;

public class WaterActivity extends ComponentActivity {

    private TextView waterText;
    private WaterViewModel waterViewModel;
    private final int GOAL = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        waterText = findViewById(R.id.water_total);
        Button btn250 = findViewById(R.id.btn_add_250);
        Button btn500 = findViewById(R.id.btn_add_500);

        waterViewModel = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(WaterViewModel.class);

        waterViewModel.getWaterIntake().observe(this, ml -> {
            waterText.setText(ml + " ml / " + GOAL + " ml");
        });

        btn250.setOnClickListener(v -> {
            waterViewModel.addWater(250);
            WaterSyncSender.sendWaterData(this, waterViewModel.getWaterIntake().getValue());
            finish();
        });

        btn500.setOnClickListener(v -> {
            waterViewModel.addWater(500);
            WaterSyncSender.sendWaterData(this, waterViewModel.getWaterIntake().getValue());
            finish();
        });
    }
}
