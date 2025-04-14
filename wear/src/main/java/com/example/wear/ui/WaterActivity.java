package com.example.wear.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.wear.R;

public class WaterActivity extends Activity {

    private int waterIntake = 0;
    private TextView waterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        waterText = findViewById(R.id.water_total);
        Button add250 = findViewById(R.id.btn_add_250);
        Button add500 = findViewById(R.id.btn_add_500);

        add250.setOnClickListener(v -> updateWater(250));
        add500.setOnClickListener(v -> updateWater(500));
    }

    private void updateWater(int ml) {
        waterIntake += ml;
        waterText.setText(waterIntake + " ml / 2000 ml");
    }
}
