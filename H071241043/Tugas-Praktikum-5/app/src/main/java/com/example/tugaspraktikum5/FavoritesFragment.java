package com.example.tugaspraktikum5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter adapter;
    private TextView tvEmptyFavorites;
    private ProgressBar progressBarFav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        tvEmptyFavorites = view.findViewById(R.id.tv_empty_favorites);
        progressBarFav = view.findViewById(R.id.progressBarFav);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooksInBackground();
    }

    private void setupRecyclerView() {
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(new ArrayList<>());
        rvFavorites.setAdapter(adapter);

        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", data);
            startActivity(intent);
        });
    }

    private void loadFavoriteBooksInBackground() {
        progressBarFav.setVisibility(View.VISIBLE);
        rvFavorites.setVisibility(View.GONE);
        tvEmptyFavorites.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> favoriteBooks = new ArrayList<>();
            for (Book book : MainActivity.libraryBooks) {
                if (book.isLiked()) {
                    favoriteBooks.add(book);
                }
            }

            handler.post(() -> {
                progressBarFav.setVisibility(View.GONE);

                if (favoriteBooks.isEmpty()) {
                    tvEmptyFavorites.setVisibility(View.VISIBLE);
                    rvFavorites.setVisibility(View.GONE);
                } else {
                    tvEmptyFavorites.setVisibility(View.GONE);
                    rvFavorites.setVisibility(View.VISIBLE);
                    adapter.setFilteredList(favoriteBooks);
                }
            });
        });
    }
}