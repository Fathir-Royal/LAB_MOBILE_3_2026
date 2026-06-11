package com.example.tp2;

import java.io.Serializable;

public class Story implements Serializable {
    private int image;
    private String title;

    public Story(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}