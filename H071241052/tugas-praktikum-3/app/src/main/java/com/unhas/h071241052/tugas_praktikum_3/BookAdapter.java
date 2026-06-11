package com.unhas.h071241052.tugas_praktikum_3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.unhas.h071241052.tugas_praktikum_3.model.Book;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private ArrayList<Book> books;

    private ArrayList<Book> booksFull;

    public BookAdapter(ArrayList<Book> books) {
        this.books = books;
        this.booksFull = new ArrayList<>(books);
    }

    public void filterList(String text) {
        books.clear();
        if (text.isEmpty()) {
            books.addAll(booksFull);
        } else {
            String filterPattern = text.toLowerCase().trim();
            for (Book item : booksFull) {
                if (item.getTitle().toLowerCase().contains(filterPattern)) {
                    books.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvYear.setText(book.getYear());

        if (book.getCoverImage().contains("content://") || book.getCoverImage().contains("file://")) {
            Glide.with(holder.itemView.getContext())
                    .load(book.getCoverImage())
                    .into(holder.ivCover);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(Integer.parseInt(book.getCoverImage()))
                    .into(holder.ivCover);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvAuthor, tvYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_book_cover);
            tvTitle = itemView.findViewById(R.id.tv_book_title);
            tvAuthor = itemView.findViewById(R.id.tv_book_author);
            tvYear = itemView.findViewById(R.id.tv_book_year);
        }
    }
}