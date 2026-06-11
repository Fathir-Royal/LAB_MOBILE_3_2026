package com.example.tugas_praktikum_2;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView rvHighlights, rvProfilePosts;
    private PostAdapter postAdapter;
    private StoryAdapter storyAdapter;

    private final ActivityResultLauncher<Intent> addPostLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Post newPost = (Post) result.getData().getSerializableExtra("NEW_POST");
                    if (newPost != null) {
                        DataSource.profilePosts.add(0, newPost); // Tambah ke feed profil [cite: 17]
                        postAdapter.notifyItemInserted(0);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        rvHighlights = findViewById(R.id.rv_highlights);
        rvProfilePosts = findViewById(R.id.rv_profile_posts);
        Button btnAddPost = findViewById(R.id.btn_add_post);

        // Setup Highlights (Horizontal) - 7 item [cite: 11, 13]
        rvHighlights.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        storyAdapter = new StoryAdapter(DataSource.stories, story -> {
            Intent intent = new Intent(ProfileActivity.this, DetailActivity.class);
            intent.putExtra("EXTRA_STORY", story);
            startActivity(intent);
        });
        rvHighlights.setAdapter(storyAdapter);

        // Setup Profile Feed - 5 item [cite: 8, 10]
        rvProfilePosts.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(DataSource.profilePosts, new PostAdapter.OnItemClickListener() {
            @Override
            public void onProfileClick(Post post) {} // Sudah di profil

            @Override
            public void onPostClick(Post post) {
                Intent intent = new Intent(ProfileActivity.this, DetailActivity.class);
                intent.putExtra("EXTRA_POST", post);
                startActivity(intent);
            }
        });
        rvProfilePosts.setAdapter(postAdapter);

        btnAddPost.setOnClickListener(v -> {
            addPostLauncher.launch(new Intent(this, AddPostActivity.class));
        });
    }
}
