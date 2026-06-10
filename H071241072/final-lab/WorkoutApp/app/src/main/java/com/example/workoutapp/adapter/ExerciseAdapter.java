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

    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListener listener;

    public ExerciseAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setExercises(List<Exercise> list) {
        this.exercises = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addExercises(List<Exercise> list) {
        if (list == null) return;
        int start = exercises.size();
        exercises.addAll(list);
        notifyItemRangeInserted(start, list.size());
    }

    public void clearExercises() {
        exercises.clear();
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
