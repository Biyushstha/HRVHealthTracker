package com.example.hrvhealthtracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HRVHealthDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "hrv_health.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_WATER = "WaterLog";

    public HRVHealthDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_WATER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT UNIQUE, " +
                "amount_ml INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER);
        onCreate(db);
    }
}
