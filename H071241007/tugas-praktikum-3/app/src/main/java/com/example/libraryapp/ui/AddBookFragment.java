package com.example.libraryapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.libraryapp.R;
import com.example.libraryapp.data.DataManager;
import com.example.libraryapp.databinding.FragmentAddBookBinding;
import com.example.libraryapp.model.Book;

public class AddBookFragment extends Fragment {

    private FragmentAddBookBinding binding;
    private Uri selectedImageUri;

    public AddBookFragment() {
        super(R.layout.fragment_add_book);
    }

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null && binding != null) {
                    selectedImageUri = uri;
                    Glide.with(requireContext())
                            .load(uri)
                            .placeholder(R.drawable.ic_book_placeholder)
                            .into(binding.ivCoverPreview);
                }
            });

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.bind(view);

        binding.btnPickImage.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        binding.btnSaveBook.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = binding.etTitle.getText().toString().trim();
        String author = binding.etAuthor.getText().toString().trim();
        String yearStr = binding.etYear.getText().toString().trim();
        String genre = binding.etGenre.getText().toString().trim();
        String ratingStr = binding.etRating.getText().toString().trim();
        String review = binding.etReview.getText().toString().trim();
        String blurb = binding.etBlurb.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(yearStr)) {
            Toast.makeText(requireContext(), getString(R.string.error_required_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        float rating = 0f;

        try {
            year = Integer.parseInt(yearStr);
            if (year < 1000 || year > 2100) {
                Toast.makeText(requireContext(), getString(R.string.error_invalid_year), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!TextUtils.isEmpty(ratingStr)) {
                rating = Float.parseFloat(ratingStr);
                if (rating < 0f || rating > 5f) {
                    Toast.makeText(requireContext(), getString(R.string.error_invalid_rating), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), getString(R.string.error_invalid_format), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(genre)) genre = "Unknown";
        if (TextUtils.isEmpty(review)) review = "-";
        if (TextUtils.isEmpty(blurb)) blurb = "-";

        String cover = selectedImageUri != null ? selectedImageUri.toString() : "";

        Book newBook = new Book(title, author, year, blurb, cover, false, rating, genre, review);
        DataManager.addBook(newBook);

        Toast.makeText(requireContext(), getString(R.string.book_added), Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private void clearForm() {
        binding.etTitle.setText("");
        binding.etAuthor.setText("");
        binding.etYear.setText("");
        binding.etGenre.setText("");
        binding.etRating.setText("");
        binding.etReview.setText("");
        binding.etBlurb.setText("");
        selectedImageUri = null;
        binding.ivCoverPreview.setImageResource(R.drawable.ic_book_placeholder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}