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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;

public class HomeFragment extends Fragment {
    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.search_view);
        rvBooks = view.findViewById(R.id.rv_books);

        progressBar = view.findViewById(R.id.progress_bar);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);

                executor.execute(() -> {
                    try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

                    adapter.filterList(newText);

                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                    });
                });
                return true;
            }
        });


        ArrayList<Book> bookList = BookRepository.getInstance().getBooks();
        adapter = new BookAdapter(bookList);

        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBooks.setAdapter(adapter);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterList(newText);
                return true;
            }
        });

        return view;
    }
}