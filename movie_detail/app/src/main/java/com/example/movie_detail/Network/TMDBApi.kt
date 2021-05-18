package com.example.movie_detail.Network

import com.example.movie_detail.Dataclasses.GetSimilarMoviesResponse
import com.example.movie_detail.Dataclasses.TMDBResourceConfig
import com.example.movie_detail.Network.Movie.MovieApi
import com.example.movie_detail.Network.Responses.GetGenres
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TMDBApi {
    @GET("movie/{movieId}")
    fun loadMovieById(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Call<MovieApi?>
    @GET("movie/{movieId}/similar")
    fun loadSimilarMovies(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Call<GetSimilarMoviesResponse?>
    @GET("configuration")
    fun getResourcesConfig(@Query("api_key") apiKey: String): Call<TMDBResourceConfig>
    @GET("genre/movie/list")
    fun getGenresList(@Query("api_key") apiKey: String): Call <GetGenres?>
}