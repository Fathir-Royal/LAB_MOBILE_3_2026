package com.example.tugaspraktikum5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddBookFragment extends Fragment {

    private ImageView imgPreview;
    private Uri selectedImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        imgPreview = view.findViewById(R.id.img_preview_cover);
        Button btnPilihGambar = view.findViewById(R.id.btn_pilih_gambar);
        Button btnSimpan = view.findViewById(R.id.btn_simpan_buku);

        EditText etJudul = view.findViewById(R.id.et_input_judul);
        EditText etPenulis = view.findViewById(R.id.et_input_penulis);
        EditText etTahun = view.findViewById(R.id.et_input_tahun);
        EditText etGenre = view.findViewById(R.id.et_input_genre);
        EditText etRating = view.findViewById(R.id.et_input_rating);
        EditText etBlurb = view.findViewById(R.id.et_input_blurb);
        EditText etReview = view.findViewById(R.id.et_input_review);

        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        imgPreview.setImageURI(selectedImageUri);
                    }
                }
        );

        btnPilihGambar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        btnSimpan.setOnClickListener(v -> {
            String judul = etJudul.getText().toString().trim();
            String penulis = etPenulis.getText().toString().trim();
            String tahunStr = etTahun.getText().toString().trim();
            String genre = etGenre.getText().toString().trim();
            String ratingStr = etRating.getText().toString().trim();
            String blurb = etBlurb.getText().toString().trim();
            String review = etReview.getText().toString().trim();

            if (judul.isEmpty() || penulis.isEmpty() || tahunStr.isEmpty() || genre.isEmpty() || ratingStr.isEmpty() || blurb.isEmpty()) {
                Toast.makeText(getContext(), "Tolong isi semua data wajib!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedImageUri == null) {
                Toast.makeText(getContext(), "Tolong pilih cover buku dari galeri!", Toast.LENGTH_SHORT).show();
                return;
            }

            int tahun = Integer.parseInt(tahunStr);
            if (tahun > 2026) {
                Toast.makeText(getContext(), "Tahun terbit tidak masuk akal (maksimal 2026)!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                String cleanRating = ratingStr.replace("/5", "").trim();
                float ratingValue = Float.parseFloat(cleanRating);

                if (ratingValue < 1.0f || ratingValue > 5.0f) {
                    Toast.makeText(getContext(), "Rating harus berada di skala 1 hingga 5!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ratingStr = cleanRating + "/5";
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Format rating salah! Masukkan angka (cth: 4.5)", Toast.LENGTH_SHORT).show();
                return;
            }

            Book newBook = new Book(judul, penulis, tahunStr, blurb, R.drawable.ic_launcher_background, genre, ratingStr, review);
            newBook.setImageUri(selectedImageUri.toString());

            MainActivity.libraryBooks.add(0, newBook);

            Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

            etJudul.setText("");
            etPenulis.setText("");
            etTahun.setText("");
            etGenre.setText("");
            etRating.setText("");
            etBlurb.setText("");
            etReview.setText("");
            imgPreview.setImageResource(0);
            selectedImageUri = null;
        });

        return view;
    }
}
