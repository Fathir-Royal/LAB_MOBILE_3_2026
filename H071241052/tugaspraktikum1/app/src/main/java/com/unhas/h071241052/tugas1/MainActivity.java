package com.unhas.h071241052.tugas1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvName, tvUsernameTop, tvBio;
    private ImageView ivProfile;
    private static final int REQUEST_CODE_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tv_name);
        tvUsernameTop = findViewById(R.id.tv_username_top);
        tvBio = findViewById(R.id.tv_bio);
        ivProfile = findViewById(R.id.iv_profile);
        Button btnEdit = findViewById(R.id.btn_edit_profile);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("key_name");
            String updatedUsername = data.getStringExtra("key_username");
            String updatedBio = data.getStringExtra("key_bio");
            String updatedImageUri = data.getStringExtra("key_image");

            if (updatedName != null && !updatedName.isEmpty()) tvName.setText(updatedName);
            if (updatedUsername != null && !updatedUsername.isEmpty()) tvUsernameTop.setText(updatedUsername);
            if (updatedBio != null && !updatedBio.isEmpty()) tvBio.setText(updatedBio);
            if (updatedImageUri != null) ivProfile.setImageURI(Uri.parse(updatedImageUri));
        }
    }
}