package com.example.workoutapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "workout.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_FAVORITES = "favorites";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_CATEGORY = "category";
    public static final String COL_CATEGORY_ID = "category_id";
    public static final String COL_SAVED_AT = "saved_at";

    private static final String CREATE_TABLE_FAVORITES =
            "CREATE TABLE " + TABLE_FAVORITES + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT NOT NULL, " +
            COL_DESCRIPTION + " TEXT, " +
            COL_CATEGORY + " TEXT, " +
            COL_CATEGORY_ID + " INTEGER DEFAULT 0, " +
            COL_SAVED_AT + " INTEGER DEFAULT 0" +
            ");";

    public static final String TABLE_EXERCISES_CACHE = "exercises_cache";
    public static final String COL_JSON = "json_data";
    public static final String COL_CACHE_KEY = "cache_key";
    public static final String COL_TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_CACHE =
            "CREATE TABLE " + TABLE_EXERCISES_CACHE + " (" +
            COL_CACHE_KEY + " TEXT PRIMARY KEY, " +
            COL_JSON + " TEXT NOT NULL, " +
            COL_TIMESTAMP + " INTEGER NOT NULL" +
            ");";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITES);
        db.execSQL(CREATE_TABLE_CACHE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES_CACHE);
        onCreate(db);
    }
}
