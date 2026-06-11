package com.example.tugaspraktikum5;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String judul;
    private String penulis;
    private String tahunTerbit;
    private String blurb;
    private int coverImage;
    private boolean isLiked;
    private String imageUri;
    private String genre;
    private String rating;
    private String review;

    public Book(String judul, String penulis, String tahunTerbit, String blurb, int coverImage, String genre, String rating, String review) {
        this.judul = formatTitleCase(judul);
        this.penulis = formatTitleCase(penulis);
        this.genre = formatTitleCase(genre);
        this.tahunTerbit = tahunTerbit;
        this.blurb = blurb;
        this.coverImage = coverImage;
        this.isLiked = false;
        this.imageUri = null;
        this.rating = rating;
        this.review = review;
    }

    public String getGenre() { return genre; }
    public String getRating() { return rating; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public String getTahunTerbit() { return tahunTerbit; }
    public String getBlurb() { return blurb; }
    public int getCoverImage() { return coverImage; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    protected Book(Parcel in) {
        judul = in.readString();
        penulis = in.readString();
        tahunTerbit = in.readString();
        blurb = in.readString();
        coverImage = in.readInt();
        isLiked = in.readByte() != 0;
        imageUri = in.readString();
        genre = in.readString();
        rating = in.readString();
        review = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(penulis);
        dest.writeString(tahunTerbit);
        dest.writeString(blurb);
        dest.writeInt(coverImage);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeString(imageUri);
        dest.writeString(genre);
        dest.writeString(rating);
        dest.writeString(review);
    }

    private String formatTitleCase(String text) {
        if (text == null || text.trim().isEmpty()) return text;
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                sb.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }
}