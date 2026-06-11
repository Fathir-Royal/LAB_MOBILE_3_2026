package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        MainAdapter adapter = new MainAdapter(
                this,
                DummyData.getHomeFeeds()
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FrameLayout btnProfile = findViewById(R.id.btnProfile);

        btnProfile.setOnClickListener(v -> {

            Feed user = DummyData.getUsers().get(2);

            Intent intent = new Intent(this, ProfileActivity.class);

            intent.putExtra("username", user.getUsername());
            intent.putExtra("profileImage", user.getProfileImage());

            startActivity(intent);
        });
    }
}