package com.example.tp3;

import android.net.Uri;

public class Book {
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String blurb;
    private Uri gambarCoverUri;
    private boolean isLiked;
    private String genre;

    public Book(String judul, String penulis, int tahunTerbit, String blurb, Uri gambarCoverUri, String genre) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.blurb = blurb;
        this.gambarCoverUri = gambarCoverUri;
        this.genre = genre;
        this.isLiked = false;
    }

    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getTahunTerbit() { return tahunTerbit; }
    public String getBlurb() { return blurb; }
    public Uri getGambarCoverUri() { return gambarCoverUri; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public String getGenre() { return genre; }
}
