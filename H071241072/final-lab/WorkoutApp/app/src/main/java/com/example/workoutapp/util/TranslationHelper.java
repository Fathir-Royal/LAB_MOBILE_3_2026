package com.example.workoutapp.util;

import java.util.HashMap;
import java.util.Map;

public class TranslationHelper {

    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();

    static {
        CATEGORY_MAP.put("Abs", "Perut");
        CATEGORY_MAP.put("Arms", "Lengan");
        CATEGORY_MAP.put("Back", "Punggung");
        CATEGORY_MAP.put("Calves", "Betis");
        CATEGORY_MAP.put("Chest", "Dada");
        CATEGORY_MAP.put("Legs", "Kaki");
        CATEGORY_MAP.put("Shoulders", "Bahu");
        CATEGORY_MAP.put("Cardio", "Kardio");
        CATEGORY_MAP.put("Core", "Inti");
    }

    public static String translateCategory(String english) {
        return CATEGORY_MAP.getOrDefault(english, english);
    }

    public static String stripHtml(String html) {
        if (html == null || html.isEmpty()) return "Tidak ada deskripsi.";
        return html.replaceAll("<[^>]*>", "").trim();
    }
}
