package com.example.tugaspraktikum5;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tugaspraktikum5.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static java.util.ArrayList<Book> libraryBooks = new java.util.ArrayList<>();
    
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "VolunteerPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Menggunakan ViewBinding untuk performa & kebersihan kode
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Muat data jika sebelumnya sudah ada
        loadData();

        binding.btnSave.setOnClickListener(v -> saveData());
        binding.btnClear.setOnClickListener(v -> clearData());

        // Logika Switch Theme (Dark Mode Dinamis)
        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            applyTheme(isChecked);
        });
    }

    private void saveData() {
        String name = binding.etName.getText().toString().trim();
        String expertise = binding.etExpertise.getText().toString().trim();
        String ageStr = binding.etAge.getText().toString().trim();

        // Validasi Field Kosong
        if (name.isEmpty() || expertise.isEmpty() || ageStr.isEmpty()) {
            if (name.isEmpty()) binding.etName.setError("Nama tidak boleh kosong!");
            if (expertise.isEmpty()) binding.etExpertise.setError("Keahlian wajib diisi!");
            if (ageStr.isEmpty()) binding.etAge.setError("Umur wajib diisi!");
            return;
        }

        // Validasi Umur Harus Angka
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            binding.etAge.setError("Umur harus berupa angka yang valid!");
            return;
        }

        // Validasi Radio Button (Jenis Kelamin)
        String gender = binding.rbMale.isChecked() ? "Pria" : (binding.rbFemale.isChecked() ? "Wanita" : "");
        if (gender.isEmpty()) {
            showSnackbar("Silakan pilih Jenis Kelamin terlebih dahulu.");
            return;
        }

        // Menyimpan data menggunakan Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NAME", name);
        editor.putString("EXPERTISE", expertise);
        editor.putInt("AGE", age);
        editor.putString("GENDER", gender);
        editor.apply();

        // Notifikasi Melayang
        showSnackbar("Data berhasil disimpan!");
        displayResult(name, expertise, age, gender);
    }

    private void loadData() {
        if (sharedPreferences.contains("NAME")) {
            String name = sharedPreferences.getString("NAME", "");
            String expertise = sharedPreferences.getString("EXPERTISE", "");
            int age = sharedPreferences.getInt("AGE", 0);
            String gender = sharedPreferences.getString("GENDER", "");

            binding.etName.setText(name);
            binding.etExpertise.setText(expertise);
            binding.etAge.setText(String.valueOf(age));

            if ("Pria".equals(gender)) {
                binding.rbMale.setChecked(true);
            } else if ("Wanita".equals(gender)) {
                binding.rbFemale.setChecked(true);
            }

            displayResult(name, expertise, age, gender);
        }
    }

    private void clearData() {
        // Membersihkan SharedPreferences
        sharedPreferences.edit().clear().apply();
        
        // Membersihkan UI
        binding.etName.setText("");
        binding.etExpertise.setText("");
        binding.etAge.setText("");
        binding.rgGender.clearCheck();
        
        binding.tvResult.setVisibility(View.GONE);
        showSnackbar("Semua data telah dihapus!");
    }

    private void displayResult(String name, String expertise, int age, String gender) {
        String result = String.format("Profil Aktif:\nNama: %s\nKeahlian: %s\nUmur: %d Tahun\nGender: %s",
                name, expertise, age, gender);
        binding.tvResult.setText(result);
        
        // Animasi Halus: Slide-up dan Fade-in
        binding.tvResult.setVisibility(View.VISIBLE);
        binding.tvResult.setAlpha(0f);
        binding.tvResult.setTranslationY(40f);
        
        binding.tvResult.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void applyTheme(boolean isDark) {
        if (isDark) {
            // Dark Mode
            binding.rootLayout.setBackgroundColor(Color.parseColor("#121212"));
            binding.cardView.setCardBackgroundColor(Color.parseColor("#1E1E1E")); 
            
            int darkText = Color.parseColor("#E0E0E0");
            binding.tvTitle.setTextColor(darkText);
            binding.tvGenderLabel.setTextColor(darkText);
            binding.tvResult.setTextColor(darkText);
            binding.switchTheme.setTextColor(darkText);
            
            binding.rbMale.setTextColor(darkText);
            binding.rbFemale.setTextColor(darkText);
        } else {
            // Light Mode (Glassmorphism / Gradasi Lembut)
            binding.rootLayout.setBackgroundColor(Color.parseColor("#E0EAFC"));
            binding.cardView.setCardBackgroundColor(Color.parseColor("#D9FFFFFF")); 
            
            int lightText = Color.parseColor("#2C3E50");
            binding.tvTitle.setTextColor(lightText);
            binding.tvGenderLabel.setTextColor(lightText);
            binding.tvResult.setTextColor(lightText);
            binding.switchTheme.setTextColor(lightText);
            
            binding.rbMale.setTextColor(lightText);
            binding.rbFemale.setTextColor(lightText);
        }
    }
}