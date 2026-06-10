package com.example.workoutapp.network;

import com.example.workoutapp.network.model.ExerciseList;
import com.example.workoutapp.network.model.MuscleGroup;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WgerApiService {

    @GET("exerciseinfo/")
    Call<ExerciseList> getExercises(
            @Query("format") String format,
            @Query("language") int language,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("exerciseinfo/")
    Call<ExerciseList> getExercisesByCategory(
            @Query("format") String format,
            @Query("language") int language,
            @Query("category") int categoryId,
            @Query("limit") int limit
    );

    @GET("muscle/")
    Call<MuscleGroup> getMuscles(
            @Query("format") String format
    );
}
