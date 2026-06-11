package com.example.tp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView rvFeed, rvHighlight;
    TextView tvUsername;
    ImageView imgProfile, btnBack;

    List<Feed> userFeeds;
    FeedAdapter feedAdapter;

    String username;
    int profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        rvFeed = findViewById(R.id.rvFeed);
        rvHighlight = findViewById(R.id.rvHighlight);
        tvUsername = findViewById(R.id.tvUsername);
        imgProfile = findViewById(R.id.imgProfile);
        btnBack = findViewById(R.id.btnBack);

        username = getIntent().getStringExtra("username");
        profileImage = getIntent().getIntExtra("profileImage", 0);

        tvUsername.setText(username);
        if (profileImage != 0) {
            imgProfile.setImageResource(profileImage);
        }

        btnBack.setOnClickListener(v -> finish());

        userFeeds = new ArrayList<>();

        feedAdapter = new FeedAdapter(this, userFeeds);
        rvFeed.setLayoutManager(new GridLayoutManager(this, 3));
        rvFeed.setAdapter(feedAdapter);

        loadFeeds();

        List<Highlight> highlightList = DummyData.getHighlights();
        rvHighlight.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        rvHighlight.setAdapter(new HighlightAdapter(this, highlightList));
    }

    private void loadFeeds() {
        userFeeds.clear();

        userFeeds.addAll(DummyData.getFeedsByUser(username));

        SharedPreferences prefs = getSharedPreferences("posts", MODE_PRIVATE);
        String imageUri = prefs.getString("last_post", null);

        if (imageUri != null && username.equals(DummyData.CURRENT_USER)) {

            boolean sudahAda = false;

            for (Feed f : userFeeds) {
                if (imageUri.equals(f.getImageUri())) {
                    sudahAda = true;
                    break;
                }
            }

            if (!sudahAda) {
                userFeeds.add(0, new Feed(
                        imageUri,
                        DummyData.CURRENT_USER,
                        DummyData.CURRENT_USER_IMAGE,
                        "Post dari galeri"
                ));
            }
        }

        feedAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFeeds();
    }
}