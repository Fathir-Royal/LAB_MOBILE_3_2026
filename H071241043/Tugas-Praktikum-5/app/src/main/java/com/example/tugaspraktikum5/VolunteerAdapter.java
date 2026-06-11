package com.example.tugaspraktikum5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Book> listBuku;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public BookAdapter(ArrayList<Book> list) {
        this.listBuku = list;
    }

    public void setFilteredList(ArrayList<Book> filteredList) {
        this.listBuku = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = listBuku.get(position);

        holder.tvJudul.setText(book.getJudul());
        holder.tvPenulisTahun.setText(book.getPenulis() + " - " + book.getTahunTerbit());
        holder.tvBlurb.setText(book.getBlurb());

        if (book.getImageUri() != null) {
            holder.imgCover.setImageURI(android.net.Uri.parse(book.getImageUri()));
        } else {
            holder.imgCover.setImageResource(book.getCoverImage());
        }

        if (book.isLiked()) {
            holder.ivStatusLike.setImageResource(android.R.drawable.star_on);
        } else {
            holder.ivStatusLike.setImageResource(android.R.drawable.star_off);
        }

        // --- TAMBAHAN BARU: BINTANG BISA DIKLIK ---
        holder.ivStatusLike.setOnClickListener(v -> {
            // Balikkan statusnya (kalau True jadi False, kalau False jadi True)
            boolean newState = !book.isLiked();
            book.setLiked(newState);

            // Langsung ubah gambar bintang di layar secara instan
            if (newState) {
                holder.ivStatusLike.setImageResource(android.R.drawable.star_on);
                android.widget.Toast.makeText(v.getContext(), "Ditambahkan ke Favorites!", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                holder.ivStatusLike.setImageResource(android.R.drawable.star_off);
                android.widget.Toast.makeText(v.getContext(), "Dihapus dari Favorites!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickCallback != null) {
                onItemClickCallback.onItemClicked(listBuku.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBuku.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover, ivStatusLike;
        TextView tvJudul, tvPenulisTahun, tvBlurb;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.img_cover);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPenulisTahun = itemView.findViewById(R.id.tv_penulis_tahun);
            tvBlurb = itemView.findViewById(R.id.tv_blurb);

            ivStatusLike = itemView.findViewById(R.id.iv_status_like);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Book data);
    }
}