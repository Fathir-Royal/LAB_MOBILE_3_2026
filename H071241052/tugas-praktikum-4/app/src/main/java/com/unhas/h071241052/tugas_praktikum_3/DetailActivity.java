package com.unhas.h071241052.tugas_praktikum_3;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.unhas.h071241052.tugas_praktikum_3.data.BookRepository;
import com.unhas.h071241052.tugas_praktikum_3.model.Book;

public class DetailActivity extends AppCompatActivity {
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String bookId = getIntent().getStringExtra("BOOK_ID");

        for (Book b : BookRepository.getInstance().getBooks()) {
            if (b.getId().equals(bookId)) {
                book = b;
                break;
            }
        }

        if (book != null) {
            setupUI();
        }
    }

    private void setupUI() {
        ImageView ivCover = findViewById(R.id.iv_detail_cover);
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvAuthor = findViewById(R.id.tv_detail_author);
        TextView tvYear = findViewById(R.id.tv_detail_year);
        TextView tvBlurb = findViewById(R.id.tv_detail_blurb);
        MaterialButton btnLike = findViewById(R.id.btn_like);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvYear.setText(book.getYear());
        tvBlurb.setText(book.getBlurb());

        Glide.with(this)
                .load(book.getCoverImage().contains("content://") ? book.getCoverImage() : Integer.parseInt(book.getCoverImage()))
                .into(ivCover);

        updateLikeButton(btnLike);

        btnLike.setOnClickListener(v -> {
            book.setFavorite(!book.isFavorite());
            updateLikeButton(btnLike);
        });
    }

    private void updateLikeButton(MaterialButton btn) {
        if (book.isFavorite()) {
            btn.setText("Berhasil Disukai");
            btn.setIconResource(R.drawable.ic_favorite);
            btn.setText("Like Buku Ini");
            btn.setIconResource(R.drawable.ic_favorite);
        }
    }
}