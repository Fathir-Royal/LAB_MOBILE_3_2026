package com.unhas.h071241052.tugaspraktikum5;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegName, etRegNIM, etRegPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegName = findViewById(R.id.etRegName);
        etRegNIM = findViewById(R.id.etRegNIM);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etRegName.getText().toString();
                String nim = etRegNIM.getText().toString();
                String pass = etRegPassword.getText().toString();

                if (name.isEmpty() || nim.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Mohon isi semua data", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name", name);
                    editor.putString("nim", nim);
                    editor.putString("password", pass);
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}