package com.example.tugas_praktikum_2;

import java.io.Serializable;

public class Post implements Serializable {
    private int id;
    private String username;
    private int profileImage;
    private int postImage;
    private String caption;

    public Post(int id, String username, int profileImage, int postImage, String caption) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.caption = caption;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getProfileImage() { return profileImage; }
    public int getPostImage() { return postImage; }
    public String getCaption() { return caption; }
}
