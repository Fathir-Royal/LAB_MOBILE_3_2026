package com.example.libraryapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.libraryapp.R;
import com.example.libraryapp.databinding.ItemBookBinding;
import com.example.libraryapp.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    private final Context context;
    private final OnBookClickListener listener;
    private final List<Book> books = new ArrayList<>();

    public BookAdapter(Context context, OnBookClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Book> data) {
        books.clear();
        books.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.binding.tvTitle.setText(book.getTitle());
        holder.binding.tvAuthor.setText(book.getAuthor());
        holder.binding.tvMeta.setText(book.getGenre() + " • " + book.getPublishYear() + " • ⭐ " + book.getRating());

        holder.binding.ivLike.setImageResource(
                book.isLiked() ? R.drawable.ic_favorite_24 : R.drawable.ic_favorite_border_24
        );
        holder.binding.ivLike.setColorFilter(
                ContextCompat.getColor(context, book.isLiked() ? R.color.favorite_active : R.color.favorite_inactive)
        );

        String cover = book.getCoverUriOrRes();
        if (!TextUtils.isEmpty(cover)) {
            Glide.with(context)
                    .load(Uri.parse(cover))
                    .placeholder(R.drawable.ic_book_placeholder)
                    .error(R.drawable.ic_book_placeholder)
                    .into(holder.binding.ivCover);
        } else {
            holder.binding.ivCover.setImageResource(R.drawable.ic_book_placeholder);
        }

        holder.itemView.setOnClickListener(v -> listener.onBookClick(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ItemBookBinding binding;
        BookViewHolder(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}