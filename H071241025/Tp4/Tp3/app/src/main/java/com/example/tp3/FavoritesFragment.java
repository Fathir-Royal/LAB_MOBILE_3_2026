package com.example.tp3;

import android.os.AsyncTask;
import android.os.Bundle;
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

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private TextView tvNoFavorites;
    private ProgressBar progressBar;
    private BookAdapter favoriteAdapter;
    private ArrayList<Book> favoriteBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        tvNoFavorites = view.findViewById(R.id.tv_no_favorites);
        progressBar = view.findViewById(R.id.progress_bar);

        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFavoritesTask().execute();
    }

    private class LoadFavoritesTask extends AsyncTask<Void, Void, ArrayList<Book>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            rvFavorites.setVisibility(View.GONE);
            tvNoFavorites.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... voids) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return DataDummy.getFavoriteBooks();
        }

        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            progressBar.setVisibility(View.GONE);
            favoriteBooks = books;

            if (favoriteAdapter == null) {
                favoriteAdapter = new BookAdapter(getContext(), favoriteBooks);
                rvFavorites.setAdapter(favoriteAdapter);
            } else {
                favoriteAdapter.updateList(favoriteBooks);
            }

            if (favoriteBooks.isEmpty()) {
                tvNoFavorites.setVisibility(View.VISIBLE);
                rvFavorites.setVisibility(View.GONE);
            } else {
                tvNoFavorites.setVisibility(View.GONE);
                rvFavorites.setVisibility(View.VISIBLE);
            }
        }
    }
}