package com.example.threadsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 2;
    private EditText etNama, etUsername, etBio;
    private ImageView ivEditProfil;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etNama = findViewById(R.id.etNama);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        ivEditProfil = findViewById(R.id.ivEditProfil);
        LinearLayout layoutGantiFoto = findViewById(R.id.layoutGantiFoto);
        TextView btnBatal = findViewById(R.id.btnBatal);
        TextView btnSelesai = findViewById(R.id.btnSelesai);

        // Isi form dengan data dari MainActivity
        Intent intentMasuk = getIntent();
        etNama.setText(intentMasuk.getStringExtra("nama"));
        etUsername.setText(intentMasuk.getStringExtra("username"));
        etBio.setText(intentMasuk.getStringExtra("bio"));
        String uriString = intentMasuk.getStringExtra("imageUri");
        if (uriString != null) {
            imageUri = Uri.parse(uriString);
            ivEditProfil.setImageURI(imageUri);
        }

        // Klik untuk ganti foto
        layoutGantiFoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
        });

        // Tombol Batal
        btnBatal.setOnClickListener(v -> finish());

        // Tombol Selesai
        btnSelesai.setOnClickListener(v -> {
            Intent intentKembali = new Intent();
            intentKembali.putExtra("nama", etNama.getText().toString());
            intentKembali.putExtra("username", etUsername.getText().toString());
            intentKembali.putExtra("bio", etBio.getText().toString());
            if (imageUri != null) {
                intentKembali.putExtra("imageUri", imageUri.toString());
            }
            setResult(RESULT_OK, intentKembali);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Grant persistable permission if needed or just use the URI
            getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ivEditProfil.setImageURI(imageUri);
        }
    }
}