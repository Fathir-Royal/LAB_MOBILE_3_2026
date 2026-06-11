package com.example.tugas_praktikum_2;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        ImageView ivImage = findViewById(R.id.iv_detail_image);
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvDesc = findViewById(R.id.tv_detail_desc);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        Post post = (Post) getIntent().getSerializableExtra("EXTRA_POST");
        Story story = (Story) getIntent().getSerializableExtra("EXTRA_STORY");

        if (post != null) {
            getSupportActionBar().setTitle(post.getUsername());
            ivImage.setImageResource(post.getPostImage());
            tvTitle.setText(post.getUsername());
            tvDesc.setText(post.getCaption());
        } else if (story != null) {
            getSupportActionBar().setTitle("Story Detail");
            ivImage.setImageResource(story.getImage());
            tvTitle.setText(story.getTitle());
            tvDesc.setText("Highlight: " + story.getTitle());
        }
    }
}
