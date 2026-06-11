package com.example.tp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvName, tvUsername, tvBio;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvBio = findViewById(R.id.tvBio);
        Button btnEdit = findViewById(R.id.btnEditProfile);

        sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        String nama = sharedPreferences.getString("nama", "zahra");
        String username = sharedPreferences.getString("username", "pzahraal");
        String bio = sharedPreferences.getString("bio", "draw a comma instead of period,");

        tvName.setText(nama);
        tvUsername.setText(username);
        tvBio.setText(bio);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

            intent.putExtra("nama", tvName.getText().toString());
            intent.putExtra("username", tvUsername.getText().toString());
            intent.putExtra("bio", tvBio.getText().toString());

            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String nama = data.getStringExtra("nama");
            String username = data.getStringExtra("username");
            String bio = data.getStringExtra("bio");

            tvName.setText(nama);
            tvUsername.setText(username);
            tvBio.setText(bio);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nama", nama);
            editor.putString("username", username);
            editor.putString("bio", bio);
            editor.apply();
        }
    }
}