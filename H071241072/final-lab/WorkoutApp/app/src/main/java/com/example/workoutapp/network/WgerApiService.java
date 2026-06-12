package com.example.workoutapp.network;

import com.example.workoutapp.network.model.ExerciseList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Definisi endpoint API wger (https://wger.de/api/v2/).
 *
 * Cukup SATU method untuk semua kasus: daftar latihan, filter kategori,
 * dan pagination. Kuncinya ada di parameter "category" yang bertipe Integer
 * (boleh null). Kalau null, Retrofit otomatis TIDAK mengirim parameter itu,
 * sehingga server mengembalikan SEMUA kategori. Kalau diisi id kategori,
 * server hanya mengembalikan latihan pada kategori tersebut.
 */
public interface WgerApiService {

    @GET("exerciseinfo/")
    Call<ExerciseList> getExercises(
            @Query("format") String format,   // selalu "json"
            @Query("language") int language,  // 2 = bahasa Inggris (paling lengkap)
            @Query("limit") int limit,        // jumlah item per halaman
            @Query("offset") int offset,      // posisi awal halaman (untuk pagination)
            @Query("category") Integer category // id kategori, atau null = semua kategori
    );
}
