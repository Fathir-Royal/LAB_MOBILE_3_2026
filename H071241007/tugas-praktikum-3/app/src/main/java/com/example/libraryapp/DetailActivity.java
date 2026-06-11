package com.example.libraryapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.libraryapp.data.DataManager;
import com.example.libraryapp.databinding.ActivityDetailBinding;
import com.example.libraryapp.model.Book;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Book book;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String bookId = getIntent().getStringExtra("book_id");
        if (bookId == null) {
            finish();
            return;
        }

        book = DataManager.getBookById(bookId);
        if (book == null) {
            Toast.makeText(this, getString(R.string.book_not_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        bindData();

        binding.btnLikeToggle.setOnClickListener(v -> {
            book.setLiked(!book.isLiked());
            updateLikeButton();
        });
    }

    private void bindData() {
        binding.tvTitleDetail.setText(book.getTitle());
        binding.tvAuthorYear.setText(book.getAuthor() + " • " + book.getPublishYear());
        binding.tvGenreRating.setText(book.getGenre() + " • ⭐ " + book.getRating());
        binding.tvReview.setText(getString(R.string.review_prefix, book.getReview()));
        binding.tvBlurb.setText(book.getBlurb());

        if (book.getCoverUriOrRes() != null && !book.getCoverUriOrRes().isEmpty()) {
            Glide.with(this)
                    .load(Uri.parse(book.getCoverUriOrRes()))
                    .placeholder(R.drawable.ic_book_placeholder)
                    .error(R.drawable.ic_book_placeholder)
                    .into(binding.ivCoverDetail);
        } else {
            binding.ivCoverDetail.setImageResource(R.drawable.ic_book_placeholder);
        }

        updateLikeButton();
    }

    private void updateLikeButton() {
        binding.btnLikeToggle.setText(book.isLiked() ? getString(R.string.liked) : getString(R.string.like));
    }
}