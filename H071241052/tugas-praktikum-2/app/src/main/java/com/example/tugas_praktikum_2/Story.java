package com.example.tugas_praktikum_2;


import java.io.Serializable;

public class Story implements Serializable {
    private int id;
    private String title;
    private int image;

    public Story(int id, String title, int image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getImage() { return image; }
}
