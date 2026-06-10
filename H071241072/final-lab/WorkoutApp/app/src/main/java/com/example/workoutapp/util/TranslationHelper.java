package com.example.workoutapp.util;

import java.util.HashMap;
import java.util.Map;

public class TranslationHelper {

    private static final Map<String, String> MUSCLE_MAP = new HashMap<>();
    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
    private static final Map<String, String> EQUIPMENT_MAP = new HashMap<>();

    static {
        // Otot
        MUSCLE_MAP.put("Anterior deltoid", "Deltoid Depan");
        MUSCLE_MAP.put("Biceps brachii", "Bisep");
        MUSCLE_MAP.put("Brachialis", "Brakialis");
        MUSCLE_MAP.put("Brachioradialis", "Brakioradialis");
        MUSCLE_MAP.put("Gastrocnemius", "Betis");
        MUSCLE_MAP.put("Gluteus maximus", "Bokong");
        MUSCLE_MAP.put("Hamstrings", "Paha Belakang");
        MUSCLE_MAP.put("Iliopsoas", "Iliopsoas");
        MUSCLE_MAP.put("Infraspinatus", "Infraspinatus");
        MUSCLE_MAP.put("Latissimus dorsi", "Otot Punggung Lebar");
        MUSCLE_MAP.put("Obliquus externus abdominis", "Otot Perut Samping");
        MUSCLE_MAP.put("Pectoralis major", "Otot Dada");
        MUSCLE_MAP.put("Quadriceps femoris", "Paha Depan");
        MUSCLE_MAP.put("Rectus abdominis", "Otot Perut");
        MUSCLE_MAP.put("Serratus anterior", "Serratus Anterior");
        MUSCLE_MAP.put("Soleus", "Soleus");
        MUSCLE_MAP.put("Tibialis anterior", "Tibialis Depan");
        MUSCLE_MAP.put("Trapezius", "Trapezius");
        MUSCLE_MAP.put("Triceps brachii", "Trisep");
        MUSCLE_MAP.put("Upper trapezius", "Trapezius Atas");

        // Kategori latihan
        CATEGORY_MAP.put("Abs", "Perut");
        CATEGORY_MAP.put("Arms", "Lengan");
        CATEGORY_MAP.put("Back", "Punggung");
        CATEGORY_MAP.put("Calves", "Betis");
        CATEGORY_MAP.put("Chest", "Dada");
        CATEGORY_MAP.put("Legs", "Kaki");
        CATEGORY_MAP.put("Shoulders", "Bahu");
        CATEGORY_MAP.put("Cardio", "Kardio");
        CATEGORY_MAP.put("Core", "Inti");

        // Alat
        EQUIPMENT_MAP.put("Barbell", "Barbel");
        EQUIPMENT_MAP.put("Bench", "Bangku");
        EQUIPMENT_MAP.put("Body weight", "Berat Badan");
        EQUIPMENT_MAP.put("Cable", "Kabel");
        EQUIPMENT_MAP.put("Dumbbell", "Dumbel");
        EQUIPMENT_MAP.put("EZ Bar", "EZ Bar");
        EQUIPMENT_MAP.put("Foam Roll", "Foam Roll");
        EQUIPMENT_MAP.put("Gym mat", "Matras Gym");
        EQUIPMENT_MAP.put("Kettlebell", "Kettlebell");
        EQUIPMENT_MAP.put("Machine", "Mesin");
        EQUIPMENT_MAP.put("None (bodyweight exercise)", "Tanpa Alat");
        EQUIPMENT_MAP.put("Pull-up bar", "Palang Pull-up");
        EQUIPMENT_MAP.put("Resistance Band", "Resistance Band");
        EQUIPMENT_MAP.put("Swiss Ball", "Swiss Ball");
    }

    public static String translateMuscle(String english) {
        return MUSCLE_MAP.getOrDefault(english, english);
    }

    public static String translateCategory(String english) {
        return CATEGORY_MAP.getOrDefault(english, english);
    }

    public static String translateEquipment(String english) {
        return EQUIPMENT_MAP.getOrDefault(english, english);
    }

    public static String stripHtml(String html) {
        if (html == null || html.isEmpty()) return "Tidak ada deskripsi.";
        return html.replaceAll("<[^>]*>", "").trim();
    }
}
