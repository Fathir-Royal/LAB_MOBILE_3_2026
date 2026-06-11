package com.example.libraryapp.model;

import java.io.Serializable;
import java.util.UUID;

public class Book implements Serializable {
    private final String id;
    private String title;
    private String author;
    private int publishYear;
    private String blurb;
    private String coverUriOrRes;
    private boolean liked;
    private float rating;
    private String genre;
    private String review;

    public Book(String title, String author, int publishYear, String blurb,
                String coverUriOrRes, boolean liked, float rating, String genre, String review) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.blurb = blurb;
        this.coverUriOrRes = coverUriOrRes;
        this.liked = liked;
        this.rating = rating;
        this.genre = genre;
        this.review = review;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublishYear() { return publishYear; }
    public String getBlurb() { return blurb; }
    public String getCoverUriOrRes() { return coverUriOrRes; }
    public boolean isLiked() { return liked; }
    public float getRating() { return rating; }
    public String getGenre() { return genre; }
    public String getReview() { return review; }

    public void setLiked(boolean liked) { this.liked = liked; }
}