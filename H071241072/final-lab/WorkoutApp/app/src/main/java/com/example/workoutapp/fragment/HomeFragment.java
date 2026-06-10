package com.example.workoutapp.fragment;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.example.workoutapp.R;
import com.example.workoutapp.activity.ExerciseDetailActivity;
import com.example.workoutapp.adapter.ExerciseAdapter;
import com.example.workoutapp.network.ApiClient;
import com.example.workoutapp.network.model.Exercise;
import com.example.workoutapp.network.model.ExerciseList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout layoutError;
    private Button btnRetry;
    private int currentOffset = 0;
    private static final int PAGE_SIZE = 20;
    private boolean isLoading = false;

    private String currentCategoryFilter = "Semua";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_exercises);
        progressBar = view.findViewById(R.id.progress_bar);
        layoutError = view.findViewById(R.id.layout_error);
        btnRetry = view.findViewById(R.id.btn_retry);
        
        SearchView searchView = view.findViewById(R.id.search_view);
        ChipGroup chipGroup = view.findViewById(R.id.chip_group_category);

        adapter = new ExerciseAdapter(this::onExerciseClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query, currentCategoryFilter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText, currentCategoryFilter);
                return false;
            }
        });

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                Chip chip = view.findViewById(checkedId);
                if (chip != null) {
                    currentCategoryFilter = chip.getText().toString();
                    adapter.filter(searchView.getQuery().toString(), currentCategoryFilter);
                }
            } else {
                currentCategoryFilter = "Semua";
                adapter.filter(searchView.getQuery().toString(), currentCategoryFilter);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
                if (!isLoading && lm != null &&
                        lm.findLastVisibleItemPosition() >= adapter.getItemCount() - 5) {
                    loadExercises(false);
                }
            }
        });

        btnRetry.setOnClickListener(v -> {
            currentOffset = 0;
            adapter.clearExercises();
            loadExercises(true);
        });

        loadExercises(true);
    }

    private void loadExercises(boolean showLoading) {
        if (isLoading) return;
        isLoading = true;
        layoutError.setVisibility(View.GONE);
        if (showLoading) progressBar.setVisibility(View.VISIBLE);

        // Language 2 = English (most exercises available in English)
        ApiClient.getApiService().getExercises("json", 2, PAGE_SIZE, currentOffset)
                .enqueue(new Callback<ExerciseList>() {
                    @Override
                    public void onResponse(@NonNull Call<ExerciseList> call,
                                           @NonNull Response<ExerciseList> response) {
                        progressBar.setVisibility(View.GONE);
                        isLoading = false;
                        if (response.isSuccessful() && response.body() != null) {
                            java.util.List<Exercise> list = response.body().getResults();
                            if (list != null && !list.isEmpty()) {
                                adapter.addExercises(list);
                                currentOffset += PAGE_SIZE;
                            } else {
                                android.util.Log.e("HomeFragment", "List is null or empty. List: " + list);
                                showError();
                            }
                        } else {
                            android.util.Log.e("HomeFragment", "Response unsuccessful or body null. Code: " + response.code());
                            showError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ExerciseList> call, @NonNull Throwable t) {
                        android.util.Log.e("HomeFragment", "Network request failed", t);
                        progressBar.setVisibility(View.GONE);
                        isLoading = false;
                        showError();
                    }
                });
    }

    private void showError() {
        if (adapter.getItemCount() == 0) {
            layoutError.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(requireContext(),
                    "Gagal memuat data. Periksa koneksi internet.", Toast.LENGTH_SHORT).show();
        }
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
