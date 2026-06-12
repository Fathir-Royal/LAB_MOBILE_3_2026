package com.example.workoutapp.network.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Exercise {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private Category category;

    @SerializedName("translations")
    private List<Translation> translations;

    public int getId() { return id; }

    public String getName() {
        if (translations != null && !translations.isEmpty()) {
            for (Translation t : translations) {
                if (t.getLanguage() == 2) {
                    return t.getName();
                }
            }
            return translations.get(0).getName();
        }
        return name != null ? name : "Latihan Tidak Diketahui";
    }

    public String getDescription() {
        if (translations != null && !translations.isEmpty()) {
            for (Translation t : translations) {
                if (t.getLanguage() == 2) {
                    return t.getDescription();
                }
            }
            return translations.get(0).getDescription();
        }
        return description != null ? description : "";
    }

    public Category getCategory() { return category; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(Category category) { this.category = category; }

    public static class Category {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
        public void setId(int id) { this.id = id; }
        public void setName(String name) { this.name = name; }
    }

    public static class Translation {
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("language")
        private int language;

        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getLanguage() { return language; }
    }
}
