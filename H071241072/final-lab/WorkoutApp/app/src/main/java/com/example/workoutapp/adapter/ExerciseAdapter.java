package com.example.workoutapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.R;
import com.example.workoutapp.network.model.Exercise;
import com.example.workoutapp.util.TranslationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter daftar latihan untuk RecyclerView.
 *
 * Tanggung jawab adapter ini SUDAH disederhanakan:
 *  - Menampilkan kartu latihan (nama, tag kategori, deskripsi singkat).
 *  - Melakukan pencarian (search) berdasarkan NAMA latihan saja.
 *
 * Filter KATEGORI tidak lagi diurus di sini. Kategori difilter langsung
 * dari server (lihat HomeFragment + WgerApiService), supaya hasilnya akurat
 * dan tidak terbatas pada data yang kebetulan sudah ter-load di layar.
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    // originalExercises = semua data yang sudah di-load (sumber kebenaran).
    // visibleExercises  = data yang benar-benar ditampilkan setelah pencarian.
    private final List<Exercise> originalExercises = new ArrayList<>();
    private final List<Exercise> visibleExercises = new ArrayList<>();

    private final OnItemClickListener listener;
    private String currentQuery = "";

    public ExerciseAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    /** Mengganti seluruh isi list (dipakai mis. oleh halaman Favorit). */
    public void setExercises(List<Exercise> list) {
        originalExercises.clear();
        if (list != null) originalExercises.addAll(list);
        applySearch();
    }

    /** Menambah data halaman berikutnya (dipakai saat infinite scroll). */
    public void addExercises(List<Exercise> list) {
        if (list == null) return;
        originalExercises.addAll(list);
        applySearch();
    }

    /** Mengosongkan list, mis. saat ganti kategori atau tekan "Coba Lagi". */
    public void clearExercises() {
        originalExercises.clear();
        visibleExercises.clear();
        notifyDataSetChanged();
    }

    /** Jumlah item yang sudah di-load dari server (sebelum difilter pencarian). */
    public int getLoadedCount() {
        return originalExercises.size();
    }

    /** Dipanggil setiap kali teks pencarian berubah. */
    public void filter(String query) {
        currentQuery = (query == null) ? "" : query.trim().toLowerCase();
        applySearch();
    }

    /**
     * Menyusun ulang daftar yang tampil berdasarkan teks pencarian.
     * Jika pencarian kosong -> tampilkan semua. Jika ada teks -> cocokkan dengan nama.
     */
    private void applySearch() {
        visibleExercises.clear();
        if (currentQuery.isEmpty()) {
            visibleExercises.addAll(originalExercises);
        } else {
            for (Exercise ex : originalExercises) {
                if (ex.getName().toLowerCase().contains(currentQuery)) {
                    visibleExercises.add(ex);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise ex = visibleExercises.get(position);

        holder.tvName.setText(ex.getName());

        // Tag kategori: nama dari API berbahasa Inggris, diterjemahkan ke Indonesia.
        String category = (ex.getCategory() != null)
                ? TranslationHelper.translateCategory(ex.getCategory().getName())
                : "Umum";
        holder.tvCategory.setText(category);

        // Deskripsi: buang tag HTML, lalu potong agar tidak terlalu panjang.
        String desc = TranslationHelper.stripHtml(ex.getDescription());
        if (desc.length() > 100) desc = desc.substring(0, 100) + "...";
        holder.tvDesc.setText(desc.isEmpty() ? "Ketuk untuk detail latihan" : desc);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(ex);
        });
    }

    @Override
    public int getItemCount() {
        return visibleExercises.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory, tvDesc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_exercise_name);
            tvCategory = itemView.findViewById(R.id.tv_exercise_category);
            tvDesc = itemView.findViewById(R.id.tv_exercise_desc);
        }
    }
}
