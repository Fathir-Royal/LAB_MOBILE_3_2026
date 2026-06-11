package com.unhas.h071241052.tugaspraktikum5;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    private EditText etNamaBahan;
    private Spinner spKategori;
    private RadioGroup rgLokasi;
    private CheckBox cbUrgent;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        etNamaBahan = findViewById(R.id.etNamaBahan);
        spKategori = findViewById(R.id.spKategori);
        rgLokasi = findViewById(R.id.rgLokasi);
        cbUrgent = findViewById(R.id.cbUrgent);
        btnSimpan = findViewById(R.id.btnSimpan);

        String[] daftarKategori = {"Daging", "Sayur", "Buah", "Susu/Olahan", "Lainnya"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, daftarKategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategori.setAdapter(adapter);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
            }
        });
    }

    private void simpanData() {
        String nama = etNamaBahan.getText().toString();
        String kategori = spKategori.getSelectedItem().toString();

        int selectedId = rgLokasi.getCheckedRadioButtonId();
        RadioButton rb = findViewById(selectedId);
        String lokasi = rb.getText().toString();

        boolean isUrgent = cbUrgent.isChecked();

        if (nama.isEmpty()) {
            Toast.makeText(this, "Nama bahan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        } else {
            String dataFinal = nama + "|" + kategori + "|" + lokasi + "|" + (isUrgent ? "Segera" : "Biasa");
            tulisKeFile(dataFinal);
            finish();
        }
    }

    private void tulisKeFile(String data) {
        String filename = "stok_makanan.txt";
        java.io.FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, android.content.Context.MODE_APPEND);
            fos.write((data + "\n").getBytes());
            android.widget.Toast.makeText(this, "Data tersimpan secara permanen!", android.widget.Toast.LENGTH_SHORT).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}