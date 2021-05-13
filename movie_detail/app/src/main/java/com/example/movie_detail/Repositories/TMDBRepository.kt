package com.example.movie_detail.Repositories

import android.util.Log
import com.example.movie_detail.Dataclasses.MovieDetailsDataclasse
import com.example.movie_detail.Dataclasses.SimilarMovieDataclasse
import com.example.movie_detail.Network.TMDBapi
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class TMDBRepository(baseUrl: String, private val apiKey: String) {
    private val moshi = Moshi.Builder().build();
    private val api by lazy {
        Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
            .create(TMDBapi::class.java)
    }

    suspend fun getMovieDetail(movieId: String) = withContext(Dispatchers.IO) {
        val movieDetail = api.loadMovieId(movieId, apiKey).await();
        movieDetail;
    }

    suspend fun getIdOfSimilarMovies(movieId: String) = withContext(Dispatchers.IO) {
        val listOfIds = api.loadSimilarMovies(movieId, apiKey).await()
        listOfIds
    }

    suspend fun getMovieDetailsFromList(listOfMoviesId: List<SimilarMovieDataclasse>) = withContext(Dispatchers.IO) {
        val detailOfMovies: MutableList<MovieDetailsDataclasse> = mutableListOf();
        listOfMoviesId.forEach {movieId: SimilarMovieDataclasse ->
            val movieDetails = getMovieDetail(movieId.id)
            detailOfMovies.plus(movieDetails)
            detailOfMovies.add(movieDetails)
        }
        detailOfMovies;
    }
}