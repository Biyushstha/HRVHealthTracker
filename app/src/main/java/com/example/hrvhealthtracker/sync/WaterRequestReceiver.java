package com.example.hrvhealthtracker.sync;

import android.util.Log;

import com.example.hrvhealthtracker.ApplicationContextProvider;
import com.google.android.gms.wearable.*;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.example.hrvhealthtracker.util.WaterLogManager;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaterRequestReceiver implements MessageClient.OnMessageReceivedListener {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();



    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if ("/water_sync".equals(messageEvent.getPath())) {
            String json = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Log.d("WaterRequestReceiver", "📩 Received data: " + json);

            executor.execute(() -> {
                try {
                    org.json.JSONObject obj = new org.json.JSONObject(json);
                    String date = obj.getString("date");
                    int amount = obj.getInt("amount");

                    WaterLogManager.insertOrUpdateWater(
                            ApplicationContextProvider.getContext(), date, amount
                    );
                } catch (Exception e) {
                    Log.e("WaterRequestReceiver", "❌ Error parsing JSON", e);
                }
            });
        }
    }
}
