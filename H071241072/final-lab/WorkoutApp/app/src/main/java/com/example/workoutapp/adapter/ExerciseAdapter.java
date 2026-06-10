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

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    private List<Exercise> originalExercises = new ArrayList<>();
    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListener listener;
    private String currentQuery = "";
    private String currentCategory = "";

    public ExerciseAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setExercises(List<Exercise> list) {
        this.originalExercises = list != null ? new ArrayList<>(list) : new ArrayList<>();
        applyFilter();
    }

    public void addExercises(List<Exercise> list) {
        if (list == null) return;
        originalExercises.addAll(list);
        applyFilter();
    }

    public void clearExercises() {
        originalExercises.clear();
        exercises.clear();
        notifyDataSetChanged();
    }

    public void filter(String query, String category) {
        this.currentQuery = query == null ? "" : query.trim();
        this.currentCategory = category == null || category.equalsIgnoreCase("Semua") ? "" : category.trim();
        applyFilter();
    }

    private void applyFilter() {
        exercises.clear();
        if (currentQuery.isEmpty() && currentCategory.isEmpty()) {
            exercises.addAll(originalExercises);
        } else {
            for (Exercise ex : originalExercises) {
                boolean matchesQuery = true;
                if (!currentQuery.isEmpty()) {
                    matchesQuery = ex.getName().toLowerCase().contains(currentQuery.toLowerCase());
                }
                boolean matchesCategory = true;
                if (!currentCategory.isEmpty()) {
                    String cat = (ex.getCategory() != null)
                            ? TranslationHelper.translateCategory(ex.getCategory().getName())
                            : "Umum";
                    matchesCategory = cat.equalsIgnoreCase(currentCategory);
                }
                
                if (matchesQuery && matchesCategory) {
                    exercises.add(ex);
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
        Exercise ex = exercises.get(position);
        holder.tvName.setText(ex.getName());

        String cat = (ex.getCategory() != null)
                ? TranslationHelper.translateCategory(ex.getCategory().getName())
                : "Umum";
        holder.tvCategory.setText(cat);

        String desc = TranslationHelper.stripHtml(ex.getDescription());
        if (desc.length() > 100) desc = desc.substring(0, 100) + "...";
        holder.tvDesc.setText(desc.isEmpty() ? "Ketuk untuk detail latihan" : desc);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(ex);
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
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
