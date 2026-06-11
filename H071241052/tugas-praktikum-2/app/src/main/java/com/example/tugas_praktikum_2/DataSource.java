package com.example.tugas_praktikum_2;


import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<Post> posts = new ArrayList<>();
    public static List<Post> profilePosts = new ArrayList<>();
    public static List<Story> stories = new ArrayList<>();

    public static void initializeData(Context context) {
        if (!posts.isEmpty()) return;

        String packageName = context.getPackageName();

        // 1. Home Posts (10 items sesuai instruksi)
        for (int i = 1; i <= 10; i++) {
            int imageRes = context.getResources().getIdentifier("img_home_" + i, "drawable", packageName);
            posts.add(new Post(i, "user_dummy_" + i, imageRes, imageRes, "Caption home ke-" + i));
        }

        // 2. Profile Posts (5 items sesuai instruksi) [cite: 10]
        for (int i = 1; i <= 5; i++) {
            int imageRes = context.getResources().getIdentifier("img_feed_" + i, "drawable", packageName);
            profilePosts.add(new Post(i + 10, "isnadia_nurf", imageRes, imageRes, "Koleksi feed ke-" + i));
        }

        // 3. Stories (7 items sesuai instruksi)
        String[] titles = {"Holiday", "Food", "Work", "Cat", "Travel", "Sport", "Music"};
        for (int i = 1; i <= 7; i++) {
            int imageRes = context.getResources().getIdentifier("img_story_" + i, "drawable", packageName);
            stories.add(new Story(i, titles[i-1], imageRes));
        }
    }
}
