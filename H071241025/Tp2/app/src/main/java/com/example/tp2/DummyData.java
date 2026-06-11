package com.example.tp2;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<Feed> getHomeFeeds() {
        List<Feed> list = new ArrayList<>();

        list.addAll(profileFeeds);

        list.add(new Feed(R.drawable.post1, "lambe_turah", R.drawable.profile1, "Caption 1"));
        list.add(new Feed(R.drawable.post2, "lambe_turah", R.drawable.profile1, "Caption 2"));
        list.add(new Feed(R.drawable.post3, "lambe_turah", R.drawable.profile1, "Caption 3"));
        list.add(new Feed(R.drawable.post4, "lambe_turah", R.drawable.profile1, "Caption 4"));
        list.add(new Feed(R.drawable.post5, "lambe_turah", R.drawable.profile1, "Caption 5"));

        list.add(new Feed(R.drawable.post6, "makassar.info", R.drawable.profile2, "Caption 6"));
        list.add(new Feed(R.drawable.post7, "makassar.info", R.drawable.profile2, "Caption 7"));
        list.add(new Feed(R.drawable.post8, "makassar.info", R.drawable.profile2, "Caption 8"));
        list.add(new Feed(R.drawable.post9, "makassar.info", R.drawable.profile2, "Caption 9"));
        list.add(new Feed(R.drawable.post10, "makassar.info", R.drawable.profile2, "Caption 10"));

        return list;

    }

    public static List<Feed> getFeedsByUser(String username) {
        List<Feed> result = new ArrayList<>();

        if (username == null) return result;

        for (Feed feed : profileFeeds) {
            if (feed.getUsername().equalsIgnoreCase(username)) {
                result.add(feed);
            }
        }

        for (Feed feed : getHomeFeeds()) {
            if (feed.getUsername().equalsIgnoreCase(username)) {
                result.add(feed);
            }
        }

        return result;
    }

    public static List<Feed> profileFeeds = new ArrayList<>();

    public static List<Highlight> getHighlights() {
        List<Highlight> list = new ArrayList<>();

        // Highlight 1
        List<Story> h1 = new ArrayList<>();
        h1.add(new Story(R.drawable.highlight1, "lambe_turah"));
        h1.add(new Story(R.drawable.highlight2, "lambe_turah"));
        h1.add(new Story(R.drawable.highlight3, "lambe_turah"));
        h1.add(new Story(R.drawable.highlight4, "lambe_turah"));
        h1.add(new Story(R.drawable.highlight5, "lambe_turah"));
        h1.add(new Story(R.drawable.highlight6, "lambe_turah"));
        h1.add(new Story(R.drawable.highlight7, "lambe_turah"));

        // Highlight 2
        List<Story> h2 = new ArrayList<>();
        h2.add(new Story(R.drawable.highlight7, "makassar.info"));
        h2.add(new Story(R.drawable.highlight6, "makassar.info"));
        h2.add(new Story(R.drawable.highlight5, "makassar.info"));
        h2.add(new Story(R.drawable.highlight4, "makassar.info"));
        h2.add(new Story(R.drawable.highlight3, "makassar.info"));
        h2.add(new Story(R.drawable.highlight2, "makassar.info"));
        h2.add(new Story(R.drawable.highlight1, "makassar.info"));

        list.add(new Highlight("random", h1));
        list.add(new Highlight("apa ya", h2));

        return list;
    }

    public static List<Feed> getUsers() {
        List<Feed> users = new ArrayList<>();

        users.add(new Feed(0, "lambe_turah", R.drawable.profile1, ""));
        users.add(new Feed(0, "makassar.info", R.drawable.profile2, ""));
        users.add(new Feed(0, "pzahraal", R.drawable.profile, ""));

        return users;
    }

    public static final String CURRENT_USER = "pzahraal";
    public static final int CURRENT_USER_IMAGE = R.drawable.profile;
}