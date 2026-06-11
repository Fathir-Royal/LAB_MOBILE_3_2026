package com.unhas.h071241052.tugas_praktikum_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unhas.h071241052.tugas_praktikum_3.data.BookRepository;
import com.unhas.h071241052.tugas_praktikum_3.model.Book;
import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private RecyclerView rvFavorite;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorite = view.findViewById(R.id.rv_favorite_books);

        ArrayList<Book> favoriteBooks = BookRepository.getInstance().getFavoriteBooks();

        adapter = new BookAdapter(favoriteBooks);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorite.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Book> updatedFavorites = BookRepository.getInstance().getFavoriteBooks();
        adapter = new BookAdapter(updatedFavorites);
        rvFavorite.setAdapter(adapter);
    }
}