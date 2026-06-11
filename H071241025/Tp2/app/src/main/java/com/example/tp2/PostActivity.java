package com.example.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    EditText etCaption;
    ImageView imgPreview;
    TextView btnPost;
    ImageView btnBack;
    int selectedImage;
    Uri selectedImageUri;

    ActivityResultLauncher<String[]> imagePicker =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri != null) {

                    getContentResolver().takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );

                    selectedImageUri = uri;
                    imgPreview.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        etCaption = findViewById(R.id.captionInput);
        btnPost = findViewById(R.id.btnPost);
        btnBack = findViewById(R.id.btnBack);
        imgPreview = findViewById(R.id.imgPreview);

        selectedImage = 0;
        imgPreview.setImageResource(R.drawable.post_placeholder);
        imgPreview.setOnClickListener(v -> {
            imagePicker.launch(new String[]{"image/*"});
        });

        btnBack.setOnClickListener(v -> finish());

        btnPost.setOnClickListener(v -> {

            String caption = etCaption.getText().toString();

            Feed newPost;

            if (selectedImageUri != null) {
                newPost = new Feed(
                        selectedImageUri.toString(),
                        DummyData.CURRENT_USER,
                        DummyData.CURRENT_USER_IMAGE,
                        caption
                );
                SharedPreferences prefs = getSharedPreferences("posts", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("last_post", selectedImageUri.toString());
                editor.apply();

            } else {
                newPost = new Feed(
                        selectedImage,
                        DummyData.CURRENT_USER,
                        DummyData.CURRENT_USER_IMAGE,
                        caption
                );
            }

            finish();
        });
    }
}