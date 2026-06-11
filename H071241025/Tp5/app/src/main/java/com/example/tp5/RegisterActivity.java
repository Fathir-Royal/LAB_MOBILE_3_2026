package com.example.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private PrefManager prefManager;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefManager tempPref = new PrefManager(this);
        if (tempPref.getThemeMode() == 1) {
            setTheme(R.style.Theme_Tp5_Dark);
        } else {
            setTheme(R.style.Theme_Tp5);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prefManager = new PrefManager(this);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);

        setupPasswordToggle(etPassword, () -> isPasswordVisible, (val) -> isPasswordVisible = val);

        setupPasswordToggle(etConfirmPassword, () -> isConfirmPasswordVisible, (val) -> isConfirmPasswordVisible = val);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(this, "Masukkan username!", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Masukkan password!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 4) {
                Toast.makeText(this, "Password minimal 4 karakter!", Toast.LENGTH_SHORT).show();
            } else if (prefManager.registerUser(username, password)) {
                prefManager.checkLogin(username, password);
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Username sudah terdaftar!", Toast.LENGTH_SHORT).show();
            }
        });

        tvLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private void setupPasswordToggle(EditText editText, Getter<Boolean> getter, Setter<Boolean> setter) {
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off, 0);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Hitung area icon (di kanan)
                int drawableEnd = 2; // index untuk compound drawable kanan
                if (editText.getCompoundDrawables()[drawableEnd] != null) {
                    int iconWidth = editText.getCompoundDrawables()[drawableEnd].getBounds().width();
                    float touchX = event.getX();
                    float rightEdge = editText.getRight() - editText.getLeft();

                    if (touchX >= rightEdge - iconWidth - 20) {
                        boolean currentState = getter.get();
                        setter.set(!currentState);

                        if (!currentState) {
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                        } else {
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off, 0);
                        }

                        editText.setSelection(editText.getText().length());
                        return true;
                    }
                }
            }
            return false;
        });
    }

    interface Getter<T> {
        T get();
    }

    interface Setter<T> {
        void set(T value);
    }
}