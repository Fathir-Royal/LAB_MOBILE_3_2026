package com.example.tp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter bookAdapter;
    private ArrayList<Book> favoriteBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteBooks = DataDummy.getFavoriteBooks();

        bookAdapter = new BookAdapter(getContext(), favoriteBooks);
        rvFavorites.setAdapter(bookAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteBooks.clear();
        favoriteBooks.addAll(DataDummy.getFavoriteBooks());
        bookAdapter.notifyDataSetChanged();
    }
}