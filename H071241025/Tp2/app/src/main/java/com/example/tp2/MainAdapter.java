package com.example.tp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Feed> feeds;

    public MainAdapter(Context context, List<Feed> feeds) {
        this.context = context;
        this.feeds = feeds;
    }

    @Override
    public int getItemCount() {
        return feeds.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        if (position == 1) return 1;
        return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);

            ImageView btnAdd = v.findViewById(R.id.btnAdd);

            btnAdd.setOnClickListener(view -> {
                Intent intent = new Intent(context, PostActivity.class);
                context.startActivity(intent);
            });

            return new RecyclerView.ViewHolder(v) {};
        }

        if (viewType == 1) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_story_container, parent, false);
            return new StoryHolder(v);
        }

        View v = LayoutInflater.from(context).inflate(R.layout.item_home_feed, parent, false);
        return new HomeFeedAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 2) {
            Feed feed = feeds.get(position - 2);

            if (feed == null ||
                    (feed.getImage() == 0 && (feed.getImageUri() == null || feed.getImageUri().isEmpty()))) {
                holder.itemView.setVisibility(View.GONE);
                return;
            } else {
                holder.itemView.setVisibility(View.VISIBLE);
            }

            HomeFeedAdapter.ViewHolder vh = (HomeFeedAdapter.ViewHolder) holder;

            vh.postImage.setImageResource(feed.getImage());

            vh.profileImage.setImageResource(feed.getProfileImage());
            vh.username.setText(feed.getUsername());
            vh.caption.setText(feed.getUsername() + " " + feed.getCaption());

            vh.username.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("username", feed.getUsername());
                intent.putExtra("profileImage", feed.getProfileImage());
                context.startActivity(intent);
            });

            vh.profileImage.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("username", feed.getUsername());
                intent.putExtra("profileImage", feed.getProfileImage());
                context.startActivity(intent);
            });

            vh.postImage.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailFeedActivity.class);

                intent.putExtra("image", feed.getImage());
                intent.putExtra("username", feed.getUsername());
                intent.putExtra("caption", feed.getCaption());
                intent.putExtra("profileImage", feed.getProfileImage());

                context.startActivity(intent);
            });
        }
    }


    static class StoryHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;

        public StoryHolder(View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.rvStoryInner);
        }
    }
}