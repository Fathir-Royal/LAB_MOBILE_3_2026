package com.example.tp1_mobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etEditName, etEditEmail, etEditPhone;
    private Button btnSave;
    private ImageView ivEditAvatarImage, btnBack;
    private TextView tvEditAvatarInitials;
    private MaterialCardView cvEditAvatar;

    private String selectedImageUriString = null;

    private final ActivityResultLauncher<String> getContentLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUriString = uri.toString();
                    showProfileImage(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etEditName = findViewById(R.id.et_edit_name);
        etEditEmail = findViewById(R.id.et_edit_email);
        etEditPhone = findViewById(R.id.et_edit_phone);
        btnSave = findViewById(R.id.btn_save);
        btnBack = findViewById(R.id.btn_back);
        cvEditAvatar = findViewById(R.id.cv_edit_avatar);
        ivEditAvatarImage = findViewById(R.id.iv_edit_avatar_image);
        tvEditAvatarInitials = findViewById(R.id.tv_edit_avatar_initials);

        // Ambil data dari Intent
        etEditName.setText(getIntent().getStringExtra("KEY_NAME"));
        etEditEmail.setText(getIntent().getStringExtra("KEY_EMAIL"));
        etEditPhone.setText(getIntent().getStringExtra("KEY_PHONE"));

        // Ambil foto lama jika ada
        String existingImage = getIntent().getStringExtra("KEY_IMAGE");
        if (existingImage != null) {
            selectedImageUriString = existingImage; // Pastikan data lama terkirim balik jika tidak diubah
            showProfileImage(Uri.parse(existingImage));
        }

        cvEditAvatar.setOnClickListener(v -> getContentLauncher.launch("image/*"));
        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String name = etEditName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent res = new Intent();
            res.putExtra("UPDATED_NAME", name);
            res.putExtra("UPDATED_EMAIL", etEditEmail.getText().toString());
            res.putExtra("UPDATED_PHONE", etEditPhone.getText().toString());
            res.putExtra("UPDATED_IMAGE_URI", selectedImageUriString);

            setResult(Activity.RESULT_OK, res);
            finish();
        });
    }

    private void showProfileImage(Uri uri) {
        ivEditAvatarImage.setVisibility(View.VISIBLE);
        tvEditAvatarInitials.setVisibility(View.GONE);
        Glide.with(this).load(uri).circleCrop().into(ivEditAvatarImage);
    }
}
