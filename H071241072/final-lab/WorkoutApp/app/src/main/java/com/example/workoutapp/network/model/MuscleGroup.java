package com.example.workoutapp.network.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MuscleGroup {
    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Muscle> results;

    public List<Muscle> getResults() { return results; }

    public static class Muscle {
        @SerializedName("id")
        private int id;
        @SerializedName("name_en")
        private String nameEn;
        @SerializedName("is_front")
        private boolean isFront;

        public int getId() { return id; }
        public String getNameEn() { return nameEn; }
        public boolean isFront() { return isFront; }
    }
}
