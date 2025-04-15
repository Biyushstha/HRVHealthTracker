package com.example.wear.sync;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

import com.example.wear.viewmodel.WaterViewModel;
import com.example.wear.utils.SharedPrefHelper;

public class WaterRequestReceiver extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent event) {
        if (event.getPath().equals("/request_water")) {
            Log.d("WaterRequestReceiver", "📥 Request received");

            Context context = getApplicationContext();
            int currentWater = SharedPrefHelper.getWaterIntake(context);

            Log.d("WaterRequestReceiver", "💧 Sending back: " + currentWater + " ml");

            // Reply to phone with water value
            Wearable.getMessageClient(context).sendMessage(
                    event.getSourceNodeId(),
                    "/reply_water",
                    String.valueOf(currentWater).getBytes(StandardCharsets.UTF_8)
            );
        }
    }
}
