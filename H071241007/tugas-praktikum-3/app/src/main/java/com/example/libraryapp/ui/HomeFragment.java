package com.example.libraryapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.libraryapp.DetailActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.adapter.BookAdapter;
import com.example.libraryapp.data.DataManager;
import com.example.libraryapp.databinding.FragmentHomeBinding;
import com.example.libraryapp.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BookAdapter adapter;
    private String selectedGenre = "All";

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.bind(view);

        adapter = new BookAdapter(requireContext(), book -> {
            Intent intent = new Intent(requireContext(), DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });

        binding.rvBooks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBooks.setAdapter(adapter);

        forceSearchViewTextBlack();
        setupGenreFilter();
        setupSearch();
        applyFilterAndSearch();
    }

    @Override
    public void onResume() {
        super.onResume();
        applyFilterAndSearch();
    }

    private void forceSearchViewTextBlack() {
        int textId = androidx.appcompat.R.id.search_src_text;
        TextView searchText = binding.searchView.findViewById(textId);
        if (searchText != null) {
            searchText.setTextColor(Color.BLACK);
            searchText.setHintTextColor(Color.BLACK);
        }
    }

    private void setupGenreFilter() {
        List<String> genres = DataManager.getAllGenres();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                genres
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGenre.setAdapter(spinnerAdapter);

        
        binding.spinnerGenre.post(() -> {
            View selectedView = binding.spinnerGenre.getSelectedView();
            if (selectedView instanceof TextView) {
                ((TextView) selectedView).setTextColor(Color.BLACK);
            }
        });

        binding.spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGenre = genres.get(position);
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(Color.BLACK);
                }
                applyFilterAndSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupSearch() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilterAndSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFilterAndSearch();
                return true;
            }
        });
    }

    private void applyFilterAndSearch() {
        String keyword = binding.searchView.getQuery() == null
                ? ""
                : binding.searchView.getQuery().toString();

        List<Book> filteredByGenre = DataManager.filterByGenre(selectedGenre);

        if (keyword.trim().isEmpty()) {
            adapter.setData(filteredByGenre);
            return;
        }

        List<Book> finalList = new ArrayList<>();
        String q = keyword.toLowerCase(Locale.getDefault());
        for (Book b : filteredByGenre) {
            if (b.getTitle().toLowerCase(Locale.getDefault()).contains(q)) {
                finalList.add(b);
            }
        }
        adapter.setData(finalList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}