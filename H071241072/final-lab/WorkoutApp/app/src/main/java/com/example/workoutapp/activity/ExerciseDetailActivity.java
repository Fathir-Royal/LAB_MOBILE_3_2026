package com.example.workoutapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutapp.R;
import com.example.workoutapp.database.FavoriteDao;
import com.example.workoutapp.network.model.Exercise;
import com.example.workoutapp.util.ThemeHelper;
import com.example.workoutapp.util.TranslationHelper;

public class ExerciseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EXERCISE_ID = "exercise_id";
    public static final String EXTRA_EXERCISE_NAME = "exercise_name";
    public static final String EXTRA_EXERCISE_DESC = "exercise_desc";
    public static final String EXTRA_EXERCISE_CATEGORY = "exercise_category";

    private FavoriteDao favoriteDao;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private int exerciseId;
    private String exerciseName, exerciseDesc, exerciseCategory;
    private Button btnFavorite;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        setSupportActionBar(findViewById(R.id.toolbar_detail));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        exerciseId = getIntent().getIntExtra(EXTRA_EXERCISE_ID, -1);
        exerciseName = getIntent().getStringExtra(EXTRA_EXERCISE_NAME);
        exerciseDesc = getIntent().getStringExtra(EXTRA_EXERCISE_DESC);
        exerciseCategory = getIntent().getStringExtra(EXTRA_EXERCISE_CATEGORY);

        favoriteDao = new FavoriteDao(this);

        setTitle(exerciseName);

        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvCategory = findViewById(R.id.tv_detail_category);
        TextView tvDesc = findViewById(R.id.tv_detail_desc);
        btnFavorite = findViewById(R.id.btn_favorite);

        tvName.setText(exerciseName);
        tvCategory.setText("Kategori: " + TranslationHelper.translateCategory(exerciseCategory));
        tvDesc.setText(TranslationHelper.stripHtml(exerciseDesc));

        checkFavoriteStatus();

        btnFavorite.setOnClickListener(v -> toggleFavorite());
    }

    private void checkFavoriteStatus() {
        favoriteDao.isFavorite(exerciseId, result -> mainHandler.post(() -> {
            isFavorite = result;
            updateFavoriteButton();
        }));
    }

    private void toggleFavorite() {
        if (isFavorite) {
            favoriteDao.removeFavorite(exerciseId, result -> mainHandler.post(() -> {
                isFavorite = false;
                updateFavoriteButton();
                Toast.makeText(this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
            }));
        } else {
            Exercise exercise = new Exercise();
            exercise.setId(exerciseId);
            exercise.setName(exerciseName);
            exercise.setDescription(exerciseDesc);
            if (exerciseCategory != null && !exerciseCategory.isEmpty()) {
                Exercise.Category cat = new Exercise.Category();
                cat.setName(exerciseCategory);
                exercise.setCategory(cat);
            }
            favoriteDao.addFavorite(exercise, result -> mainHandler.post(() -> {
                isFavorite = true;
                updateFavoriteButton();
                Toast.makeText(this, "Ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
            }));
        }
    }

    private void updateFavoriteButton() {
        btnFavorite.setText(isFavorite ? "★ Hapus Favorit" : "☆ Simpan Favorit");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
