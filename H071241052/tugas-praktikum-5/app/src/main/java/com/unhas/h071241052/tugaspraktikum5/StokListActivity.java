package com.unhas.h071241052.tugaspraktikum5;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StokListActivity extends AppCompatActivity {

    private ListView lvStok;
    private ArrayList<String> daftarBahan;
    private BahanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_list);

        lvStok = findViewById(R.id.lvStok);
        daftarBahan = new ArrayList<>();

        bacaDataDariFile();

        adapter = new BahanAdapter(this, daftarBahan);
        lvStok.setAdapter(adapter);
    }

    private void bacaDataDariFile() {
        String filename = "stok_makanan.txt";
        try {
            FileInputStream fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                daftarBahan.add(line);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal memuat data atau file masih kosong", Toast.LENGTH_SHORT).show();
        }
    }
}
