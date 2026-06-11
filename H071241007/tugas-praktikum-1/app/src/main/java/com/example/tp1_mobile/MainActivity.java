package com.example.tp1_mobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private TextView tvMainName, tvMainEmail, tvMainPhone, tvMainInitials;
    private ImageButton btnEdit;
    private ImageView ivMainAvatarImage;

    // Variabel global untuk menyimpan URI foto saat ini
    private String currentImageUri = null;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();

                    String newName = data.getStringExtra("UPDATED_NAME");
                    String newEmail = data.getStringExtra("UPDATED_EMAIL");
                    String newPhone = data.getStringExtra("UPDATED_PHONE");
                    String imageUriString = data.getStringExtra("UPDATED_IMAGE_URI");

                    tvMainName.setText(newName);
                    tvMainEmail.setText(newEmail);
                    tvMainPhone.setText(newPhone);

                    // Update Foto Profil
                    if (imageUriString != null) {
                        currentImageUri = imageUriString; // Simpan ke variabel global
                        ivMainAvatarImage.setVisibility(View.VISIBLE);
                        tvMainInitials.setVisibility(View.GONE);

                        Glide.with(this)
                                .load(Uri.parse(imageUriString))
                                .circleCrop()
                                .into(ivMainAvatarImage);
                    } else {
                        // Jika foto dihapus atau tidak ada, tampilkan inisial
                        ivMainAvatarImage.setVisibility(View.GONE);
                        tvMainInitials.setVisibility(View.VISIBLE);
                        if (newName != null && !newName.isEmpty()) {
                            tvMainInitials.setText(newName.substring(0, 1).toUpperCase());
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMainName = findViewById(R.id.tv_main_name);
        tvMainEmail = findViewById(R.id.tv_main_email);
        tvMainPhone = findViewById(R.id.tv_main_phone);
        tvMainInitials = findViewById(R.id.tv_main_initials);
        ivMainAvatarImage = findViewById(R.id.iv_main_avatar_image);
        btnEdit = findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("KEY_NAME", tvMainName.getText().toString());
            intent.putExtra("KEY_EMAIL", tvMainEmail.getText().toString());
            intent.putExtra("KEY_PHONE", tvMainPhone.getText().toString());

            // CRITICAL: Kirim URI foto lama agar muncul di preview halaman edit
            if (currentImageUri != null) {
                intent.putExtra("KEY_IMAGE", currentImageUri);
            }

            editProfileLauncher.launch(intent);
        });
    }
}