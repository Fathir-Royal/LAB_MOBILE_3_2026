package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText etNama, etUsername, etBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etNama = findViewById(R.id.editNama);
        etUsername = findViewById(R.id.editUsername);
        etBio = findViewById(R.id.editBio);
        Button btnSave = findViewById(R.id.btnSave);

        String nama = getIntent().getStringExtra("nama");
        String username = getIntent().getStringExtra("username");
        String bio = getIntent().getStringExtra("bio");

        etNama.setText(nama);
        etUsername.setText(username);
        etBio.setText(bio);

        btnSave.setOnClickListener(v -> {
            Intent resultIntent = new Intent();

            resultIntent.putExtra("nama", etNama.getText().toString());
            resultIntent.putExtra("username", etUsername.getText().toString());
            resultIntent.putExtra("bio", etBio.getText().toString());

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}