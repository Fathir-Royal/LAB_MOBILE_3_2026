package com.example.tugaspraktikum5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private boolean isLikedState;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imgCover = findViewById(R.id.detail_img_cover);
        TextView tvJudul = findViewById(R.id.detail_tv_judul);
        TextView tvPenulisTahun = findViewById(R.id.detail_tv_penulis_tahun);
        TextView tvBlurb = findViewById(R.id.detail_tv_blurb);
        Button btnLike = findViewById(R.id.btn_like);
        TextView tvGenre = findViewById(R.id.detail_tv_genre);
        TextView tvRating = findViewById(R.id.detail_tv_rating);
        TextView tvReview = findViewById(R.id.detail_tv_review);

        ImageView btnBack = findViewById(R.id.btn_back);

        // Logika ketika tombol back kiri atas diklik
        btnBack.setOnClickListener(v -> {
            finish(); // Ini perintah bawaan Android untuk menutup halaman saat ini dan kembali ke halaman sebelumnya
        });

        Book book = getIntent().getParcelableExtra("EXTRA_BOOK");

        if (book != null) {
            if (book.getImageUri() != null) {
                imgCover.setImageURI(android.net.Uri.parse(book.getImageUri()));
            } else {
                imgCover.setImageResource(book.getCoverImage());
            }
            tvJudul.setText(book.getJudul());
            tvPenulisTahun.setText(book.getPenulis() + " - " + book.getTahunTerbit());
            tvBlurb.setText(book.getBlurb());
            tvGenre.setText("Genre: " + book.getGenre());
            tvRating.setText("Rating: " + book.getRating());

            if (book.getReview() != null && !book.getReview().isEmpty()) {
                tvReview.setText("\"" + book.getReview() + "\"");
            } else {
                tvReview.setText("Belum ada review untuk buku ini.");
            }

            isLikedState = book.isLiked();
            updateButtonAppearance(btnLike);

            btnLike.setOnClickListener(v -> {
                isLikedState = !isLikedState;
                updateButtonAppearance(btnLike);

                for (int i = 0; i < MainActivity.libraryBooks.size(); i++) {
                    if (MainActivity.libraryBooks.get(i).getJudul().equals(book.getJudul())) {
                        MainActivity.libraryBooks.get(i).setLiked(isLikedState);
                        break;
                    }
                }

                String pesan = isLikedState ? "Ditambahkan ke Favorites!" : "Dihapus dari Favorites!";
                Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateButtonAppearance(Button btn) {
        if (isLikedState) {
            btn.setText("Hapus dari Favorite");
            btn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            btn.setText("Tambahkan ke Favorite");
            btn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }
}