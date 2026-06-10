package com.example.workoutapp.network.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ExerciseList {
    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("results")
    private List<Exercise> results;

    public int getCount() { return count; }
    public String getNext() { return next; }
    public List<Exercise> getResults() { return results; }
}
