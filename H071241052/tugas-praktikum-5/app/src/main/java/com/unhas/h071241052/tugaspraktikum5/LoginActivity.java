package com.unhas.h071241052.tugaspraktikum5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etNIM, etPassword;
    private Button btnLogin;
    private TextView tvToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNIM = findViewById(R.id.etNIM);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvToRegister = findViewById(R.id.tvToRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNIM = etNIM.getText().toString().trim();
                String inputPass = etPassword.getText().toString().trim();

                SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String registeredNIM = pref.getString("nim", "");
                String registeredPass = pref.getString("password", "");

                if (inputNIM.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inputNIM.equals(registeredNIM) && inputPass.equals(registeredPass)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("EXTRA_NIM", inputNIM);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "NIM atau Password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}