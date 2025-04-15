package com.example.hrvhealthtracker.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hrvhealthtracker.data.HRVHealthDBHelper;

public class WaterLogManager {

    public static void insertOrUpdateWater(Context context, String date, int amount) {
        HRVHealthDBHelper helper = new HRVHealthDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        Log.d("WaterLogManager", "💾 Saving to DB: " + date + " → " + amount + " ml");

        db.execSQL("INSERT OR REPLACE INTO WaterLog (date, amount_ml) VALUES (?, ?)", new Object[]{date, amount});
        db.close();
    }

    public static int getWaterForDate(Context context, String date) {
        HRVHealthDBHelper helper = new HRVHealthDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT amount_ml FROM " + HRVHealthDBHelper.TABLE_WATER +
                " WHERE date = ?", new String[]{date});

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return result;
    }
}
