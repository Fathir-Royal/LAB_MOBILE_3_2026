package com.unhas.h071241052.tugas_praktikum_3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unhas.h071241052.tugas_praktikum_3.data.BookRepository;
import com.unhas.h071241052.tugas_praktikum_3.model.Book;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment {
    private RecyclerView rvFavorite;
    private BookAdapter adapter;
    private ProgressBar progressBar;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorite = view.findViewById(R.id.rv_favorite_books);
        progressBar = view.findViewById(R.id.progress_bar);

        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadFavoriteData();

        return view;
    }

    private void loadFavoriteData() {
        progressBar.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

            ArrayList<Book> favoriteBooks = BookRepository.getInstance().getFavoriteBooks();

            handler.post(() -> {
                adapter = new BookAdapter(favoriteBooks);
                rvFavorite.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteData();
    }
}