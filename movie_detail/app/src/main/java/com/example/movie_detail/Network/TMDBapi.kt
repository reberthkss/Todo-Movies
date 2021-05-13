package com.example.movie_detail.Network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBapi {
    @GET("movie/{movieId}")
    fun loadMovieId(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Call<Any>
}