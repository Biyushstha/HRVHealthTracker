package com.example.hrvhealthtracker.sync;

import android.util.Log;

import com.example.hrvhealthtracker.util.WaterLogManager;
import com.google.android.gms.wearable.*;

public class WaterSyncService extends WearableListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("WaterSyncService", "🚀 Service created");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("WaterSyncService", "📡 onDataChanged SERVICE called!");

        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    "/water_log".equals(event.getDataItem().getUri().getPath())) {

                DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                int amount = dataMap.getInt("amount");
                String date = dataMap.getString("date");

                Log.d("WaterSyncService", "💧 Synced from watch: " + amount + " ml on " + date);
                System.out.println(amount);

                // Save using your own logic (SharedPrefs or DB)
                WaterLogManager.insertOrUpdateWater(this, date, amount);

            }
        }
    }
}
