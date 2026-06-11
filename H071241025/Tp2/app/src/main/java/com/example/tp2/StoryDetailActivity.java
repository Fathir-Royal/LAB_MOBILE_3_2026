package com.example.tp2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StoryDetailActivity extends AppCompatActivity {

    ImageView imgStory;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        imgStory = findViewById(R.id.imgStory);
        tvTitle = findViewById(R.id.tvTitle);

        int image = getIntent().getIntExtra("image", 0);
        String title = getIntent().getStringExtra("title");

        imgStory.setImageResource(image);
        tvTitle.setText(title);
    }
}