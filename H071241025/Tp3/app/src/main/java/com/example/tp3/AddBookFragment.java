package com.example.tp3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;

public class AddBookFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivCoverPreview;
    private TextInputEditText etJudul, etPenulis, etTahun, etGenre, etBlurb;
    private Button btnPilihGambar, btnSimpan;

    private Uri selectedImageUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        ivCoverPreview = view.findViewById(R.id.iv_cover_preview);
        etJudul = view.findViewById(R.id.et_judul);
        etPenulis = view.findViewById(R.id.et_penulis);
        etTahun = view.findViewById(R.id.et_tahun);
        etGenre = view.findViewById(R.id.et_genre);
        etBlurb = view.findViewById(R.id.et_blurb);
        btnPilihGambar = view.findViewById(R.id.btn_pilih_gambar);
        btnSimpan = view.findViewById(R.id.btn_simpan);

        btnPilihGambar.setOnClickListener(v -> openGallery());
        btnSimpan.setOnClickListener(v -> saveBook());

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ivCoverPreview.setImageURI(selectedImageUri);
        }
    }

    private void saveBook() {
        String judul = etJudul.getText().toString().trim();
        String penulis = etPenulis.getText().toString().trim();
        String tahunStr = etTahun.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();

        if (judul.isEmpty()) {
            etJudul.setError("Judul harus diisi");
            return;
        }
        if (penulis.isEmpty()) {
            etPenulis.setError("Penulis harus diisi");
            return;
        }
        if (tahunStr.isEmpty()) {
            etTahun.setError("Tahun terbit harus diisi");
            return;
        }
        if (genre.isEmpty()) {
            etGenre.setError("Genre harus diisi");
            return;
        }
        if (blurb.isEmpty()) {
            etBlurb.setError("Sinopsis harus diisi");
            return;
        }

        int tahun = Integer.parseInt(tahunStr);

        Uri coverUri = selectedImageUri != null ? selectedImageUri :
                Uri.parse("android.resource://com.example.libraryapp/drawable/ic_book_cover");

        Book newBook = new Book(judul, penulis, tahun, blurb, coverUri, genre);
        DataDummy.addBook(newBook);

        etJudul.setText("");
        etPenulis.setText("");
        etTahun.setText("");
        etGenre.setText("");
        etBlurb.setText("");
        ivCoverPreview.setImageResource(R.drawable.ic_book_cover);
        selectedImageUri = null;

        Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
    }
}