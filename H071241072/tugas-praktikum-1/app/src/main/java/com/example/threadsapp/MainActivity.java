package com.example.threadsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT_PROFILE = 1;

    private TextView tvNama, tvUsername, tvBio;
    private ImageView ivProfil;
    private Uri profileImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNama = findViewById(R.id.tvNama);
        tvUsername = findViewById(R.id.tvUsername);
        tvBio = findViewById(R.id.tvBio);
        ivProfil = findViewById(R.id.ivProfil);
        Button btnEditProfil = findViewById(R.id.btnEditProfil);

        btnEditProfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("nama", tvNama.getText().toString());
            intent.putExtra("username", tvUsername.getText().toString());
            intent.putExtra("bio", tvBio.getText().toString());
            if (profileImageUri != null) {
                intent.putExtra("imageUri", profileImageUri.toString());
            }
            startActivityForResult(intent, REQUEST_EDIT_PROFILE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == RESULT_OK && data != null) {
            tvNama.setText(data.getStringExtra("nama"));
            tvUsername.setText(data.getStringExtra("username"));
            tvBio.setText(data.getStringExtra("bio"));
            
            String uriString = data.getStringExtra("imageUri");
            if (uriString != null) {
                profileImageUri = Uri.parse(uriString);
                ivProfil.setImageURI(profileImageUri);
            }
        }
    }
}