package com.unhas.h071241052.tugas_praktikum_3;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.unhas.h071241052.tugas_praktikum_3.data.BookRepository;
import com.unhas.h071241052.tugas_praktikum_3.model.Book;

public class AddBookFragment extends Fragment {
    private ImageView ivCover;
    private TextInputEditText etTitle, etAuthor, etYear, etBlurb;
    private String selectedImageUri = "";

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri.toString();
                    ivCover.setImageURI(uri);
                    ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        ivCover = view.findViewById(R.id.iv_add_cover);
        etTitle = view.findViewById(R.id.et_title);
        etAuthor = view.findViewById(R.id.et_author);
        etYear = view.findViewById(R.id.et_year);
        etBlurb = view.findViewById(R.id.et_blurb);
        MaterialButton btnSave = view.findViewById(R.id.btn_save);

        view.findViewById(R.id.card_add_image).setOnClickListener(v -> galleryLauncher.launch("image/*"));

        btnSave.setOnClickListener(v -> saveBook());

        return view;
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String year = etYear.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || year.isEmpty() || blurb.isEmpty() || selectedImageUri.isEmpty()) {
            Toast.makeText(getActivity(), "Harap isi semua data dan pilih gambar!", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = String.valueOf(System.currentTimeMillis());
        Book newBook = new Book(id, title, author, year, blurb, selectedImageUri);

        BookRepository.getInstance().addBook(newBook);

        Toast.makeText(getActivity(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }
}