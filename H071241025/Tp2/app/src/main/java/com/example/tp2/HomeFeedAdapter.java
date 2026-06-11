package com.example.tp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.ViewHolder> {

    private Context context;
    private List<Feed> feedList;

    public HomeFeedAdapter(Context context, List<Feed> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = feedList.get(position);

        if (feed.getImageUri() != null && !feed.getImageUri().isEmpty()) {
            holder.postImage.setImageURI(Uri.parse(feed.getImageUri()));
        } else {
            holder.postImage.setImageResource(feed.getImage());
        }

        holder.profileImage.setImageResource(feed.getProfileImage());
        holder.username.setText(feed.getUsername());
        holder.caption.setText(feed.getUsername() + " " + feed.getCaption());

        View.OnClickListener profileClickListener = v -> {
            Intent intent = new Intent(context, ProfileActivity.class);

            intent.putExtra("username", feed.getUsername());
            intent.putExtra("profileImage", feed.getProfileImage());

            context.startActivity(intent);
        };

        holder.username.setOnClickListener(profileClickListener);
        holder.profileImage.setOnClickListener(profileClickListener);

        View.OnClickListener detailClickListener = v -> {
            Intent intent = new Intent(context, DetailFeedActivity.class);

            intent.putExtra("POST_IMAGE", feed.getImage());
            intent.putExtra("USERNAME", feed.getUsername());
            intent.putExtra("PROFILE_IMAGE", feed.getProfileImage());
            intent.putExtra("CAPTION", feed.getCaption());

            context.startActivity(intent);
        };

        holder.postImage.setOnClickListener(detailClickListener);
        holder.caption.setOnClickListener(detailClickListener);

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage, profileImage, likeButton, commentButton, shareButton, saveButton;
        TextView username, caption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.postImage);
            profileImage = itemView.findViewById(R.id.profileImage);
            username = itemView.findViewById(R.id.tvUsername);
            caption = itemView.findViewById(R.id.caption);
            likeButton = itemView.findViewById(R.id.likeButton);
            commentButton = itemView.findViewById(R.id.commentButton);
            shareButton = itemView.findViewById(R.id.shareButton);
            saveButton = itemView.findViewById(R.id.saveButton);

        }

    }
}
