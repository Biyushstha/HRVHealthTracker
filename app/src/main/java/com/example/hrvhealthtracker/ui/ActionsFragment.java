package com.example.hrvhealthtracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrvhealthtracker.R;
import com.example.hrvhealthtracker.sync.WaterRequestSender;
import com.example.hrvhealthtracker.util.WaterLogManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActionsFragment extends Fragment {

    private TextView waterValueText;
    private RecyclerView calendarRecycler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actions, container, false);

        waterValueText = view.findViewById(R.id.water_value);
        calendarRecycler = view.findViewById(R.id.calendar_recycler);

        // 🗓️ Setup Calendar RecyclerView
        calendarRecycler.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Date> past7Days = getLast7Days();
        CalendarAdapter adapter = new CalendarAdapter(getContext(), past7Days);
        calendarRecycler.setAdapter(adapter);

        updateWaterUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        WaterRequestSender.requestWaterFromWatch(getContext());
    }

    public void updateWaterUI() {
        if (waterValueText != null && getContext() != null) {
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            int water = WaterLogManager.getWaterForDate(getContext(), today);
            Log.d("ActionsFragment", "💧 UI Update: " + today + " → " + water + " ml");

            waterValueText.setText(water + " ml");
        } else {
            Log.w("ActionsFragment", "❌ waterValueText or context is null");
        }
    }

    private List<Date> getLast7Days() {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 6; i >= 0; i--) {
            Calendar c = (Calendar) calendar.clone();
            c.add(Calendar.DAY_OF_YEAR, -i);
            dates.add(c.getTime());
        }
        return dates;
    }
}
