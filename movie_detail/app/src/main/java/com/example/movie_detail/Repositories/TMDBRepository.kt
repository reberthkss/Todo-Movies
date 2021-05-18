package com.example.movie_detail.Repositories

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.movie_detail.Network.Genre.MovieGenreApi
import com.example.movie_detail.Network.Movie.MovieApi
import com.example.movie_detail.Network.TMDBapirefactor
import com.example.movie_detail.R
import com.example.movie_detail.Room.CrossReference.MovieAndGenreCf
import com.example.movie_detail.Room.Database
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class TMDBRepository(baseUrl: String, private val apiKey: String, val context: Context) {
    private val TAG = TMDBRepository::class.toString()
    private val moshi = Moshi.Builder().build();
    private val api by lazy {
        Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
            .create(TMDBapirefactor::class.java)
    }
    private val database by lazy {
        Room
            .databaseBuilder(context, Database::class.java, "reberth_app")
            .fallbackToDestructiveMigration()
            .build()
    }

    suspend fun getMovieDetailsById(movieId: String) = withContext(Dispatchers.IO) {
        val movieDetail = api.loadMovieById(movieId, apiKey).await();
        movieDetail?.apply {
            val movieEntity = MovieEntity.fromApi(this);
            this.genres
                .map { MovieAndGenreCf(movieId.toLong(), it.genreId) }
                .apply {
                    database.movieAndGenre().insertManyMovieAndGenreCf(this);
                }
            database.movie().insertOneMovie(movieEntity);
        }
    }

    suspend fun getSimilarlyMoviesOfId(movieId: String) = withContext(Dispatchers.IO) {
        val similarMovieIds = api.loadSimilarMovies(movieId, apiKey).await()?.results;
        val similarMoviesEntity = mutableListOf<MovieEntity>();
        similarMovieIds?.forEach { similarMovie ->
            val movie = api.loadMovieById(similarMovie.id, apiKey).await();
            if (movie != null) similarMoviesEntity.add(MovieEntity.fromApi(movie));
        }
        database.movie().insertManyMovies(similarMoviesEntity);
    }

    suspend fun getAllGenres() = withContext(Dispatchers.IO) {
        val genres = api.getGenresList(apiKey).await()?.genres;
        val genresEntity = genres?.map {GenreEntity.fromApi(it)} ?: listOf();
        database.genre().insertManyGenres(genresEntity);
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