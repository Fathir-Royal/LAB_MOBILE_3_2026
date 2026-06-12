package com.example.workoutapp.fragment;

import android.content.Intent;
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

import com.google.android.material.chip.ChipGroup;

import com.example.workoutapp.R;
import com.example.workoutapp.activity.ExerciseDetailActivity;
import com.example.workoutapp.adapter.ExerciseAdapter;
import com.example.workoutapp.network.ApiClient;
import com.example.workoutapp.network.model.Exercise;
import com.example.workoutapp.network.model.ExerciseList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    // Bahasa 2 = Inggris (paling banyak latihannya di API wger).
    private static final int LANGUAGE_EN = 2;
    // Berapa banyak latihan yang diambil per request (pagination).
    private static final int PAGE_SIZE = 20;

    private ExerciseAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout layoutError;
    private SearchView searchView;

    // --- Status pagination & filter ---
    private int currentOffset = 0;      // posisi awal halaman berikutnya
    private boolean isLoading = false;  // mencegah request dobel
    private boolean hasMore = true;     // apakah server masih punya data berikutnya

    // id kategori yang sedang dipilih. null = chip "Semua" (tanpa filter kategori).
    private Integer currentCategoryId = null;

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

        RecyclerView recyclerView = view.findViewById(R.id.rv_exercises);
        progressBar = view.findViewById(R.id.progress_bar);
        layoutError = view.findViewById(R.id.layout_error);
        Button btnRetry = view.findViewById(R.id.btn_retry);
        searchView = view.findViewById(R.id.search_view);
        ChipGroup chipGroup = view.findViewById(R.id.chip_group_category);

        adapter = new ExerciseAdapter(this::onExerciseClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // 1) PENCARIAN: hanya menyaring nama latihan dari data yang sudah di-load (sisi klien).
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        // 2) FILTER KATEGORI (chip): memuat ulang data dari server sesuai kategori.
        //    Dengan begini hasil filter akurat, bukan cuma dari 20 item yang kebetulan tampil.
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            currentCategoryId = categoryIdFor(checkedId);
            reloadFromStart();
        });

        // 3) INFINITE SCROLL: muat halaman berikutnya saat mendekati dasar list.
        //    Pagination hanya jalan ketika TIDAK sedang mencari, supaya jumlah item
        //    yang dijadikan acuan (loaded count) konsisten dengan yang tampil.
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
                if (lm == null || dy <= 0) return; // hanya saat scroll ke bawah
                boolean isSearching = !searchView.getQuery().toString().trim().isEmpty();
                boolean nearBottom = lm.findLastVisibleItemPosition() >= adapter.getLoadedCount() - 5;
                if (!isLoading && hasMore && !isSearching && nearBottom) {
                    loadExercises(false);
                }
            }
        });

        btnRetry.setOnClickListener(v -> reloadFromStart());

        // Muat data pertama kali.
        loadExercises(true);
    }

    /**
     * Mengubah id chip yang dipilih menjadi id kategori milik API wger.
     * Mengembalikan null untuk chip "Semua" atau bila tidak ada yang terpilih.
     */
    private Integer categoryIdFor(int checkedId) {
        if (checkedId == R.id.chip_kardio)   return 15; // Cardio
        if (checkedId == R.id.chip_kaki)     return 9;  // Legs
        if (checkedId == R.id.chip_dada)     return 11; // Chest
        if (checkedId == R.id.chip_punggung) return 12; // Back
        if (checkedId == R.id.chip_lengan)   return 8;  // Arms
        if (checkedId == R.id.chip_perut)    return 10; // Abs
        if (checkedId == R.id.chip_bahu)     return 13; // Shoulders
        return null; // chip "Semua" / tidak ada pilihan
    }

    /** Reset semua status lalu memuat ulang dari halaman pertama. */
    private void reloadFromStart() {
        currentOffset = 0;
        hasMore = true;
        adapter.clearExercises();
        loadExercises(true);
    }

    /**
     * Memuat latihan dari server.
     * @param showLoading true untuk memuat dari awal (tampilkan spinner besar).
     */
    private void loadExercises(boolean showLoading) {
        if (isLoading) return;
        isLoading = true;
        layoutError.setVisibility(View.GONE);
        if (showLoading) progressBar.setVisibility(View.VISIBLE);

        // currentCategoryId boleh null -> Retrofit otomatis tidak mengirim param category.
        ApiClient.getApiService()
                .getExercises("json", LANGUAGE_EN, PAGE_SIZE, currentOffset, currentCategoryId)
                .enqueue(new Callback<ExerciseList>() {
                    @Override
                    public void onResponse(@NonNull Call<ExerciseList> call,
                                           @NonNull Response<ExerciseList> response) {
                        progressBar.setVisibility(View.GONE);
                        isLoading = false;

                        if (!response.isSuccessful() || response.body() == null) {
                            showError();
                            return;
                        }

                        List<Exercise> list = response.body().getResults();
                        if (list != null && !list.isEmpty()) {
                            adapter.addExercises(list);
                            currentOffset += list.size();
                            // Kalau server tidak punya halaman berikutnya, hentikan pagination.
                            hasMore = response.body().getNext() != null;
                        } else {
                            // Tidak ada (lagi) data untuk kategori ini.
                            hasMore = false;
                            showError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ExerciseList> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        isLoading = false;
                        showError();
                    }
                });
    }

    /**
     * Tampilkan layar error hanya jika list benar-benar kosong.
     * Kalau sudah ada data, cukup beri Toast agar yang sudah tampil tidak hilang.
     */
    private void showError() {
        if (adapter.getLoadedCount() == 0) {
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
