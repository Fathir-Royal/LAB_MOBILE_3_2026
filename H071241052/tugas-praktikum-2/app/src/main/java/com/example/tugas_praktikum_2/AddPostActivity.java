package com.example.tugas_praktikum_2;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        EditText etCaption = findViewById(R.id.et_caption);
        Button btnUpload = findViewById(R.id.btn_upload);

        btnUpload.setOnClickListener(v -> {
            String caption = etCaption.getText().toString();
            // Buat objek post baru untuk dikirim balik
            Post newPost = new Post(0, "isnadia_nurf", R.drawable.img_story_1, R.drawable.img_feed_1, caption);

            Intent intent = new Intent();
            intent.putExtra("NEW_POST", newPost);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
