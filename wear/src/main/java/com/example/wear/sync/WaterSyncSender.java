package com.example.wear.sync;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WaterSyncSender {

    public static void sendWaterData(Context context, int amount) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String message = "{\"date\":\"" + date + "\",\"amount\":" + amount + "}";

        Wearable.getNodeClient(context).getConnectedNodes().addOnSuccessListener(nodes -> {
            for (Node node : nodes) {
                Wearable.getMessageClient(context).sendMessage(
                                node.getId(), "/water_sync", message.getBytes()
                        ).addOnSuccessListener(i -> Log.d("WaterSyncSender", "✅ Sent to " + node.getDisplayName()))
                        .addOnFailureListener(e -> Log.e("WaterSyncSender", "❌ Send failed", e));
            }
        });
    }
}
