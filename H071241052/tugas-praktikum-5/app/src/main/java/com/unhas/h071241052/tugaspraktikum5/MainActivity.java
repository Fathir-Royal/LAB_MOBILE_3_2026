package com.unhas.h071241052.tugaspraktikum5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbStock;
    private TextView tvStockStatus;
    private Button btnGoToInput, btnGoToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvWelcomeNIM = findViewById(R.id.tvWelcomeNIM);
        String nim = getIntent().getStringExtra("EXTRA_NIM");
        if (nim != null && !nim.isEmpty()) {
            tvWelcomeNIM.setText("Selamat Datang, " + nim);
        }

        pbStock = findViewById(R.id.pbStock);
        tvStockStatus = findViewById(R.id.tvStockStatus);
        btnGoToInput = findViewById(R.id.btnGoToInput);
        btnGoToList = findViewById(R.id.btnGoToList);

        btnGoToInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        btnGoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StokListActivity.class);
                startActivity(intent);
            }
        });
        ImageButton btnGoToSetting = findViewById(R.id.btnGoToSetting);

        btnGoToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateVisualisasiStok();
    }

    private void updateVisualisasiStok() {
        if (pbStock == null || tvStockStatus == null) return;

        String filename = "stok_makanan.txt";
        int jumlahUrgent = 0;
        int totalData = 0;

        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                totalData++;
                if (line.contains("Segera")) {
                    jumlahUrgent++;
                }
            }
            fis.close();

            if (totalData > 0) {
                int persentase = (jumlahUrgent * 100) / totalData;
                pbStock.setProgress(persentase);
                tvStockStatus.setText("Status: " + jumlahUrgent + " dari " + totalData + " bahan segera dimasak");
            } else {
                pbStock.setProgress(0);
                tvStockStatus.setText("Belum ada data stok.");
            }

        } catch (Exception e) {
            pbStock.setProgress(0);
            tvStockStatus.setText("Mulai masukkan stok makanan Anda.");
        }
    }
}