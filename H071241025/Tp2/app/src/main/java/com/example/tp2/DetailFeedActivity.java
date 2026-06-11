package com.example.tp2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailFeedActivity extends AppCompatActivity {

    ImageView imgPost, imgProfile;
    TextView tvUsername, tvCaption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        imgPost = findViewById(R.id.detailImage);
        imgProfile = findViewById(R.id.profileImage);
        tvUsername = findViewById(R.id.tvUsername);
        tvCaption = findViewById(R.id.tvCaption);

        int image = getIntent().getIntExtra("image", 0);
        String imageUri = getIntent().getStringExtra("imageUri");

        String username = getIntent().getStringExtra("username");
        String caption = getIntent().getStringExtra("caption");
        int profileImage = getIntent().getIntExtra("profileImage", 0);

        if (username == null) username = "user";
        if (caption == null) caption = "";

        if (imageUri != null) {
            imgPost.setImageURI(Uri.parse(imageUri));
        } else {
            imgPost.setImageResource(image);
        }

        imgProfile.setImageResource(profileImage != 0 ? profileImage : R.drawable.profile1);

        tvUsername.setText(username);
        tvCaption.setText(username + " " + caption);
    }
}