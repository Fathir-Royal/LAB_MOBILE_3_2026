package com.example.tp2;

import java.util.List;

public class Highlight {
    String title;
    List<Story> stories;

    public Highlight(String title, List<Story> stories) {
        this.title = title;
        this.stories = stories;
    }

    public String getTitle() { return title; }
    public List<Story> getStories() { return stories; }

    public int getImage() {
        if (stories != null && !stories.isEmpty()) {
            return stories.get(0).getImage();
        }
        return 0;
    }
}
