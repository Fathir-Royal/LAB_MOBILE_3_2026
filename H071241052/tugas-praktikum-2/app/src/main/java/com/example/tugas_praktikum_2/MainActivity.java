package com.example.tugas_praktikum_2;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvPosts;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Penting: Inisialisasi data dummy
        DataSource.initializeData(this);

        rvPosts = findViewById(R.id.rv_posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        // Gunakan DataSource.posts (10 item)
        postAdapter = new PostAdapter(DataSource.posts, new PostAdapter.OnItemClickListener() {
            @Override
            public void onProfileClick(Post post) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("EXTRA_POST", post);
                startActivity(intent);
            }

            @Override
            public void onPostClick(Post post) {
                // Sesuai instruksi: Klik item (foto/user) pindah ke profil
                onProfileClick(post);
            }
        });
        rvPosts.setAdapter(postAdapter);
    }
}