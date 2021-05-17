package com.example.movie_detail.Repositories

import com.example.movie_detail.Dataclasses.MovieGenreApi
import com.example.movie_detail.Dataclasses.SimilarMovieDataclasse
import com.example.movie_detail.Network.Movie.MovieApi
import com.example.movie_detail.Network.TMDBapirefactor
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
            .create(TMDBapirefactor::class.java)
    }

    suspend fun getMovieDetailsById(movieId: String) = withContext(Dispatchers.IO) {
        val movieDetail = api.loadMovieById(movieId, apiKey).await();
        movieDetail;
    }

    suspend fun getSimilarlyMoviesOfId(movieId: String): List<MovieApi> = withContext(Dispatchers.IO) {
        val similarMovies = mutableListOf<MovieApi>();
        val similarMovieIds = api.loadSimilarMovies(movieId, apiKey).await()?.results;
        similarMovieIds?.forEach { similarMovie ->
            val movie = api.loadMovieById(similarMovie.id, apiKey).await();
            if (movie != null) similarMovies.add(movie);
        }
        similarMovies;
    }

    suspend fun getAllGenres(): List<MovieGenreApi>? = withContext(Dispatchers.IO) {
        val genres = api.getGenresList(apiKey).await()?.genres;
        /*TODO - Save in db*/
        genres;
    }

    suspend fun getResourcesConfiguration() = withContext(Dispatchers.IO) {
        try {
            val resourcesServerConfig = api.getResourcesConfig(apiKey).await()
            resourcesServerConfig
        } catch (e: Exception) {
            null
        }
    }
}