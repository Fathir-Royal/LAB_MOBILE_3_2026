package com.example.tp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    Context context;
    List<Feed> list;

    public FeedAdapter(Context context, List<Feed> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = list.get(position);

        if (feed.getImageUri() != null) {
            holder.image.setImageURI(Uri.parse(feed.getImageUri()));
        } else {
            holder.image.setImageResource(feed.getImage());
        }

        holder.image.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailFeedActivity.class);

            if (feed.getImageUri() != null) {
                intent.putExtra("imageUri", feed.getImageUri());
            } else {
                intent.putExtra("image", feed.getImage());
            }

            intent.putExtra("username", feed.getUsername());
            intent.putExtra("caption", feed.getCaption());
            intent.putExtra("profileImage", feed.getProfileImage());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.feedImage);
        }
    }
}