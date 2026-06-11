package com.example.tugas_praktikum_2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onProfileClick(Post post);
        void onPostClick(Post post);
    }

    public PostAdapter(List<Post> postList, OnItemClickListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(postList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivFeed;
        TextView tvUsername, tvCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile_post);
            ivFeed = itemView.findViewById(R.id.iv_feed_content);
            tvUsername = itemView.findViewById(R.id.tv_username_post);
            tvCaption = itemView.findViewById(R.id.tv_caption_post);
        }

        public void bind(final Post post, final OnItemClickListener listener) {
            tvUsername.setText(post.getUsername());
            tvCaption.setText(post.getCaption());
            ivProfile.setImageResource(post.getProfileImage());
            ivFeed.setImageResource(post.getPostImage());

            // Klik pada foto profil atau username (Pindah ke Profile)
            ivProfile.setOnClickListener(v -> listener.onProfileClick(post));
            tvUsername.setOnClickListener(v -> listener.onProfileClick(post));

            // Klik pada gambar feed (Pindah ke Detail)
            ivFeed.setOnClickListener(v -> listener.onPostClick(post));
        }
    }
}
