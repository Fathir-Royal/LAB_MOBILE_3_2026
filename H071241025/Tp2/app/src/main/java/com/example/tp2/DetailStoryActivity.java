package com.example.tp2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DetailStoryActivity extends AppCompatActivity {

    ImageView storyImage;
    TextView storyTitle;

    List<Story> stories;
    int currentIndex = 0;
    LinearLayout progressContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        storyImage = findViewById(R.id.storyImageDetail);
        storyTitle = findViewById(R.id.storyTitleDetail);
        progressContainer = findViewById(R.id.progressContainer);

        stories = (List<Story>) getIntent().getSerializableExtra("stories");

        if (stories == null || stories.size() == 0) {
            finish();
            return;
        }

        setupProgressBars();
        showStory();

        storyImage.setOnClickListener(v -> {
            currentIndex++;

            if (currentIndex < stories.size()) {
                showStory();
            } else {
                finish();
            }
        });
    }

    private void showStory() {
        Story story = stories.get(currentIndex);

        storyImage.setImageResource(story.getImage());
        storyTitle.setText(story.getTitle());

        updateProgress();
    }

    private void setupProgressBars() {
        progressContainer.removeAllViews();

        for (int i = 0; i < stories.size(); i++) {
            View bar = new View(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 4, 1);
            params.setMargins(4, 0, 4, 0);

            bar.setLayoutParams(params);
            bar.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

            progressContainer.addView(bar);
        }
    }

    private void updateProgress() {
        for (int i = 0; i < progressContainer.getChildCount(); i++) {
            View bar = progressContainer.getChildAt(i);

            if (i <= currentIndex) {
                bar.setBackgroundColor(getResources().getColor(android.R.color.white));
            } else {
                bar.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }
}