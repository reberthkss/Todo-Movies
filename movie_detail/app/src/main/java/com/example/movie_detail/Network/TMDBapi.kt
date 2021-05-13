package com.example.movie_detail.Network

import com.example.movie_detail.Dataclasses.GetSimilarMoviesResponse
import com.example.movie_detail.Dataclasses.MovieDetailsDataclasse
import com.example.movie_detail.Dataclasses.TMDBResourceConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBapi {
    @GET("movie/{movieId}")
    fun loadMovieId(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Call<MovieDetailsDataclasse>
    @GET("movie/{movieId}/similar")
    fun loadSimilarMovies(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Call<GetSimilarMoviesResponse>
    @GET("configuration")
    fun getResourcesConfig(@Query("api_key") apiKey: String): Call<TMDBResourceConfig>
}