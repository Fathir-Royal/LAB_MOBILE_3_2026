package com.example.workoutapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.workoutapp.network.model.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteDao {

    private final DatabaseHelper dbHelper;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public interface Callback<T> {
        void onResult(T result);
    }

    public FavoriteDao(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void addFavorite(Exercise exercise, Callback<Boolean> callback) {
        executor.execute(() -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_ID, exercise.getId());
            values.put(DatabaseHelper.COL_NAME, exercise.getName());
            values.put(DatabaseHelper.COL_DESCRIPTION, exercise.getDescription());
            values.put(DatabaseHelper.COL_CATEGORY,
                    exercise.getCategory() != null ? exercise.getCategory().getName() : "Umum");
            values.put(DatabaseHelper.COL_CATEGORY_ID,
                    exercise.getCategory() != null ? exercise.getCategory().getId() : 0);
            values.put(DatabaseHelper.COL_SAVED_AT, System.currentTimeMillis());

            long result = db.insertWithOnConflict(
                    DatabaseHelper.TABLE_FAVORITES, null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            if (callback != null) callback.onResult(result != -1);
        });
    }

    public void removeFavorite(int exerciseId, Callback<Boolean> callback) {
        executor.execute(() -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int rows = db.delete(DatabaseHelper.TABLE_FAVORITES,
                    DatabaseHelper.COL_ID + "=?",
                    new String[]{String.valueOf(exerciseId)});
            if (callback != null) callback.onResult(rows > 0);
        });
    }

    public void isFavorite(int exerciseId, Callback<Boolean> callback) {
        executor.execute(() -> {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DatabaseHelper.TABLE_FAVORITES,
                    new String[]{DatabaseHelper.COL_ID},
                    DatabaseHelper.COL_ID + "=?",
                    new String[]{String.valueOf(exerciseId)},
                    null, null, null);
            boolean fav = cursor.getCount() > 0;
            cursor.close();
            if (callback != null) callback.onResult(fav);
        });
    }

    public void getAllFavorites(Callback<List<Exercise>> callback) {
        executor.execute(() -> {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DatabaseHelper.TABLE_FAVORITES,
                    null, null, null, null, null,
                    DatabaseHelper.COL_SAVED_AT + " DESC");

            List<Exercise> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                Exercise e = new Exercise();
                e.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)));
                e.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)));
                e.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DESCRIPTION)));
                
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_ID));
                
                Exercise.Category category = new Exercise.Category();
                category.setName(categoryName);
                category.setId(categoryId);
                e.setCategory(category);
                
                list.add(e);
            }
            cursor.close();
            if (callback != null) callback.onResult(list);
        });
    }

    public void saveCache(String key, String json, Callback<Void> callback) {
        executor.execute(() -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_CACHE_KEY, key);
            values.put(DatabaseHelper.COL_JSON, json);
            values.put(DatabaseHelper.COL_TIMESTAMP, System.currentTimeMillis());
            db.insertWithOnConflict(DatabaseHelper.TABLE_EXERCISES_CACHE,
                    null, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (callback != null) callback.onResult(null);
        });
    }

    public void getCache(String key, Callback<String> callback) {
        executor.execute(() -> {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DatabaseHelper.TABLE_EXERCISES_CACHE,
                    new String[]{DatabaseHelper.COL_JSON},
                    DatabaseHelper.COL_CACHE_KEY + "=?",
                    new String[]{key}, null, null, null);
            String json = null;
            if (cursor.moveToFirst()) {
                json = cursor.getString(0);
            }
            cursor.close();
            if (callback != null) callback.onResult(json);
        });
    }
}
