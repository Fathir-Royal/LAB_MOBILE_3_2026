package com.example.tp2;

public class Feed {
    private int image;
    private String username;
    private int profileImage;
    private String caption;
    private String imageUri;

    public Feed(int image, String username, int profileImage, String caption) {
        this.image = image;
        this.username = username;
        this.profileImage = profileImage;
        this.caption = caption;
    }

    public Feed(String imageUri, String username, int profileImage, String caption) {
        this.imageUri = imageUri;
        this.username = username;
        this.profileImage = profileImage;
        this.caption = caption;
    }

    public int getImage() {
        return image;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getUsername() {
        return username;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public String getCaption() {
        return caption;
    }
}