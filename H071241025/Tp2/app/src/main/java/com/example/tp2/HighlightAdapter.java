package com.example.tp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

    Context context;
    List<Highlight> list;

    public HighlightAdapter(Context context, List<Highlight> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_highlight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Highlight highlight = list.get(position);

        holder.image.setImageResource(
                highlight.getStories().get(0).getImage()
        );

        holder.title.setText(highlight.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailStoryActivity.class);
            intent.putExtra("stories", new ArrayList<>(highlight.getStories()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.highlightImage);
            title = itemView.findViewById(R.id.highlightTitle);
        }
    }
}


