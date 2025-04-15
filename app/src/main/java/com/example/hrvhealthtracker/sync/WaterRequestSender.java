package com.example.hrvhealthtracker.sync;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.wearable.*;

public class WaterRequestSender {

    public static void requestWaterFromWatch(Context context) {
        Wearable.getNodeClient(context).getConnectedNodes().addOnSuccessListener(nodes -> {
            for (Node node : nodes) {
                Wearable.getMessageClient(context)
                        .sendMessage(node.getId(), "/request_water", new byte[0])
                        .addOnSuccessListener(i -> Log.d("WaterRequestSender", "✅ Request sent to " + node.getDisplayName()))
                        .addOnFailureListener(e -> Log.e("WaterRequestSender", "❌ Failed to request", e));
            }
        });
    }
}
