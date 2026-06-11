package com.example.tp3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentHome extends Fragment {

    private RecyclerView rvBooks;
    private TextView tvNoResult;
    private ProgressBar progressBar;
    private SearchView searchView;
    private Spinner spinnerGenre;
    private Spinner spinnerSort;
    private BookAdapter bookAdapter;
    private ArrayList<Book> originalList;
    private ArrayList<Book> displayList;

    private String currentGenreFilter = "Semua Genre";
    private String currentSearchQuery = "";
    private int currentSortType = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBooks = view.findViewById(R.id.rv_books);
        tvNoResult = view.findViewById(R.id.tv_no_result);
        progressBar = view.findViewById(R.id.progress_bar);
        searchView = view.findViewById(R.id.search_view);
        spinnerGenre = view.findViewById(R.id.spinner_genre);
        spinnerSort = view.findViewById(R.id.spinner_sort);

        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        originalList = DataDummy.getAllBooks();
        displayList = new ArrayList<>(originalList);

        bookAdapter = new BookAdapter(getContext(), displayList);
        rvBooks.setAdapter(bookAdapter);

        setupGenreSpinner();
        setupSortSpinner();
        setupSearchView();

        return view;
    }

    private void setupGenreSpinner() {
        ArrayList<String> genreList = new ArrayList<>();
        genreList.add("Semua Genre");

        for (Book book : originalList) {
            String[] genres = book.getGenre().split(", ");
            for (String g : genres) {
                if (!genreList.contains(g)) {
                    genreList.add(g);
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genreList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentGenreFilter = genreList.get(position);
                new FilterBooksTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSortSpinner() {
        String[] sortOptions = {"Tahun Terbaru", "Tahun Terlama", "Judul A-Z", "Judul Z-A"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sortOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(adapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSortType = position;
                new FilterBooksTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearchQuery = query;
                new FilterBooksTask().execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchQuery = newText;
                new FilterBooksTask().execute();
                return true;
            }
        });
    }

    private class FilterBooksTask extends AsyncTask<Void, Void, ArrayList<Book>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            rvBooks.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... voids) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> filteredList = new ArrayList<>();

            for (Book book : originalList) {
                boolean matchGenre = true;
                boolean matchSearch = true;

                if (!currentGenreFilter.equals("Semua Genre")) {
                    matchGenre = false;
                    String[] bookGenres = book.getGenre().split(", ");
                    for (String g : bookGenres) {
                        if (g.equals(currentGenreFilter)) {
                            matchGenre = true;
                            break;
                        }
                    }
                }

                if (!currentSearchQuery.isEmpty()) {
                    String query = currentSearchQuery.toLowerCase();
                    matchSearch = book.getJudul().toLowerCase().contains(query) ||
                            book.getPenulis().toLowerCase().contains(query);
                }

                if (matchGenre && matchSearch) {
                    filteredList.add(book);
                }
            }

            switch (currentSortType) {
                case 0:
                    Collections.sort(filteredList, (a, b) -> b.getTahunTerbit() - a.getTahunTerbit());
                    break;
                case 1:
                    Collections.sort(filteredList, (a, b) -> a.getTahunTerbit() - b.getTahunTerbit());
                    break;
                case 2:
                    Collections.sort(filteredList, (a, b) -> a.getJudul().compareToIgnoreCase(b.getJudul()));
                    break;
                case 3:
                    Collections.sort(filteredList, (a, b) -> b.getJudul().compareToIgnoreCase(a.getJudul()));
                    break;
            }

            return filteredList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> filteredList) {
            progressBar.setVisibility(View.GONE);
            displayList.clear();
            displayList.addAll(filteredList);
            bookAdapter.updateList(displayList);

            if (displayList.isEmpty()) {
                tvNoResult.setVisibility(View.VISIBLE);
                rvBooks.setVisibility(View.GONE);
            } else {
                tvNoResult.setVisibility(View.GONE);
                rvBooks.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        originalList = DataDummy.getAllBooks();
        new FilterBooksTask().execute();
    }
}