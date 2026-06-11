package com.unhas.h071241052.tugas1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class EditProfileActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView ivEditProfile;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    if (ivEditProfile != null && imageUri != null) {
                        ivEditProfile.setImageURI(imageUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        CardView cvEditProfile = findViewById(R.id.cv_edit_profile);
        ivEditProfile = findViewById(R.id.iv_edit_profile_image);
        EditText etName = findViewById(R.id.et_edit_name);
        EditText etUsername = findViewById(R.id.et_edit_username);
        EditText etBio = findViewById(R.id.et_edit_bio);
        Button btnSave = findViewById(R.id.btn_save);

        cvEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newUsername = etUsername.getText().toString();
            String newBio = etBio.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("key_name", newName);
            intent.putExtra("key_username", newUsername);
            intent.putExtra("key_bio", newBio);

            if (imageUri != null) {
                intent.putExtra("key_image", imageUri.toString());
            }

            setResult(RESULT_OK, intent);
            finish();
        });
    }
}