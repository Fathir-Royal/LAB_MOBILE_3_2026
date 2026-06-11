package com.unhas.h071241052.tugas_praktikum_3.model;

public class Book {
    private String id;
    private String title;
    private String author;
    private String year;
    private String blurb;
    private String coverImage;
    private boolean isFavorite;

    public Book(String id, String title, String author, String year, String blurb, String coverImage) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverImage = coverImage;
        this.isFavorite = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getCoverImage() { return coverImage; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}