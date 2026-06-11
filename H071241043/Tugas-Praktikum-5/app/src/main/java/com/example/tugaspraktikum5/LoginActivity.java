package com.example.tugaspraktikum5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etNim, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences autoLoginPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = autoLoginPrefs.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        etNim = findViewById(R.id.et_login_nim);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvGoToRegister = findViewById(R.id.tv_go_to_register);

        btnLogin.setOnClickListener(v -> {
            String inputNim = etNim.getText().toString().trim();
            String inputPassword = etPassword.getText().toString().trim();

            if (inputNim.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "NIM dan Password harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences loginPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

                String savedNim = loginPrefs.getString("registered_nim", "");
                String savedPassword = loginPrefs.getString("registered_password", "");

                if (inputNim.equals(savedNim) && inputPassword.equals(savedPassword)) {
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "NIM atau Password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}