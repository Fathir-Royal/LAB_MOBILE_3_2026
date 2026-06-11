package com.example.workoutapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.R;
import com.example.workoutapp.activity.ExerciseDetailActivity;
import com.example.workoutapp.adapter.ExerciseAdapter;
import com.example.workoutapp.database.FavoriteDao;
import com.example.workoutapp.network.model.Exercise;

import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private TextView tvEmpty;
    private FavoriteDao favoriteDao;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_favorites);
        tvEmpty = view.findViewById(R.id.tv_empty_favorites);

        favoriteDao = new FavoriteDao(requireContext());
        adapter = new ExerciseAdapter(this::onExerciseClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        loadFavorites();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        favoriteDao.getAllFavorites(list -> mainHandler.post(() -> {
            if (list == null || list.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.setExercises(list);
            }
        }));
    }

    private void onExerciseClick(Exercise exercise) {
        Intent intent = new Intent(requireContext(), ExerciseDetailActivity.class);
        intent.putExtra(ExerciseDetailActivity.EXTRA_EXERCISE_ID, exercise.getId());
        intent.putExtra(ExerciseDetailActivity.EXTRA_EXERCISE_NAME, exercise.getName());
        intent.putExtra(ExerciseDetailActivity.EXTRA_EXERCISE_DESC, exercise.getDescription());
        intent.putExtra(ExerciseDetailActivity.EXTRA_EXERCISE_CATEGORY,
                exercise.getCategory() != null ? exercise.getCategory().getName() : "Umum");
        startActivity(intent);
    }
}
