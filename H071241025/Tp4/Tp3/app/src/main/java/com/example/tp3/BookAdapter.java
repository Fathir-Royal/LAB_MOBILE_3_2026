package com.example.tp3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> bookList;
    private ArrayList<Book> bookListFull;

    public BookAdapter(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        this.bookListFull = new ArrayList<>(bookList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvJudul.setText(book.getJudul());
        holder.tvPenulis.setText(book.getPenulis());
        holder.tvTahun.setText(String.valueOf(book.getTahunTerbit()));
        holder.tvGenre.setText(book.getGenre());

        if (book.getGambarCoverUri() != null) {
            holder.ivCover.setImageURI(book.getGambarCoverUri());
        }

        if (book.isLiked()) {
            holder.ivLike.setImageResource(R.drawable.ic_like_filled);
        } else {
            holder.ivLike.setImageResource(R.drawable.ic_like_outline);
        }

        holder.ivLike.setOnClickListener(v -> {
            boolean newLikeState = !book.isLiked();
            book.setLiked(newLikeState);
            notifyItemChanged(position);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("judul", book.getJudul());
            intent.putExtra("penulis", book.getPenulis());
            intent.putExtra("tahun", book.getTahunTerbit());
            intent.putExtra("blurb", book.getBlurb());
            intent.putExtra("genre", book.getGenre());
            intent.putExtra("isLiked", book.isLiked());
            if (book.getGambarCoverUri() != null) {
                intent.putExtra("gambarUri", book.getGambarCoverUri().toString());
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void filter(String text) {
        bookList.clear();
        if (text.isEmpty()) {
            bookList.addAll(bookListFull);
        } else {
            String lowerCase = text.toLowerCase();
            for (Book book : bookListFull) {
                if (book.getJudul().toLowerCase().contains(lowerCase)) {
                    bookList.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateList(ArrayList<Book> newList) {
        this.bookList = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover, ivLike;
        TextView tvJudul, tvPenulis, tvTahun, tvGenre;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            ivLike = itemView.findViewById(R.id.iv_like);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPenulis = itemView.findViewById(R.id.tv_penulis);
            tvTahun = itemView.findViewById(R.id.tv_tahun);
            tvGenre = itemView.findViewById(R.id.tv_genre);
        }
    }
}
