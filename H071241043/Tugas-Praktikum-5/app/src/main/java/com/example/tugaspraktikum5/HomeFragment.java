package com.example.tugaspraktikum5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private ArrayList<Book> bookList;
    private String currentSearchQuery = "";
    private String currentSelectedGenre = "Semua Genre";
    private android.widget.ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBooks = view.findViewById(R.id.rv_books);
        SearchView searchView = view.findViewById(R.id.search_view);
        Spinner spinnerGenre = view.findViewById(R.id.spinner_filter_genre);
        progressBar = view.findViewById(R.id.progressBar);

        bookList = ((MainActivity) getActivity()).libraryBooks;

        setupRecyclerView();
        setupSpinner(spinnerGenre);
        setupSearchView(searchView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        applyFilters();
    }

    private void setupRecyclerView() {
        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(bookList);
        rvBooks.setAdapter(adapter);

        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", data);
            startActivity(intent);
        });
    }

    private void setupSpinner(Spinner spinner) {
        HashSet<String> uniqueGenres = new HashSet<>();

        for (Book book : bookList) {
            String[] pecahGenre = book.getGenre().split("/");
            for (String g : pecahGenre) {
                String cleanG = g.trim();
                if (!cleanG.isEmpty()) {
                    String[] words = cleanG.split("\\s+");
                    StringBuilder sb = new StringBuilder();
                    for (String w : words) {
                        if (w.length() > 0) {
                            sb.append(w.substring(0, 1).toUpperCase())
                                    .append(w.substring(1).toLowerCase())
                                    .append(" ");
                        }
                    }
                    uniqueGenres.add(sb.toString().trim());

                }
            }
        }

        List<String> genreList = new ArrayList<>(uniqueGenres);
        Collections.sort(genreList);

        genreList.add(0, "Semua Genre");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.item_spinner, genreList);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSelectedGenre = genreList.get(position);
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchQuery = newText;
                applyFilters();
                return true;
            }
        });
    }

    private void applyFilters() {
        progressBar.setVisibility(View.VISIBLE);
        rvBooks.setVisibility(View.GONE);

        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
        android.os.Handler handler = new android.os.Handler(android.os.Looper.getMainLooper());

        executor.execute(() -> {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> filteredList = new ArrayList<>();

            for (Book book : bookList) {
                boolean matchesSearch = book.getJudul().toLowerCase().contains(currentSearchQuery.toLowerCase()) ||
                        book.getPenulis().toLowerCase().contains(currentSearchQuery.toLowerCase());

                boolean matchesGenre = currentSelectedGenre.equals("Semua Genre") ||
                        book.getGenre().toLowerCase().contains(currentSelectedGenre.toLowerCase());

                if (matchesSearch && matchesGenre) {
                    filteredList.add(book);
                }
            }

            handler.post(() -> {
                if (adapter != null) {
                    adapter.setFilteredList(filteredList);
                }
                progressBar.setVisibility(View.GONE);
                rvBooks.setVisibility(View.VISIBLE);
            });
        });
    }
}