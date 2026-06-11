package com.example.tugaspraktikum5; // Sesuaikan dengan namamu ya!

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etNim, etPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNim = findViewById(R.id.et_register_nim);
        etPassword = findViewById(R.id.et_register_password);
        btnRegister = findViewById(R.id.btn_register);
        tvGoToLogin = findViewById(R.id.tv_go_to_login);

        btnRegister.setOnClickListener(v -> {
            String nim = etNim.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (nim.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "NIM dan Password harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

                String savedNim = sharedPreferences.getString("registered_nim", "");

                if (nim.equals(savedNim)) {
                    Toast.makeText(this, "Akun dengan NIM ini sudah terdaftar! Silakan langsung Login.", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("registered_nim", nim);
                    editor.putString("registered_password", password);
                    editor.apply();

                    Toast.makeText(this, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        tvGoToLogin.setOnClickListener(v -> {
            finish();
        });
    }
}