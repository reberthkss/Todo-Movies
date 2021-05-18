package com.example.movie_detail.Repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.example.movie_detail.Network.Genre.MovieGenreApi
import com.example.movie_detail.Network.Movie.MovieApi
import com.example.movie_detail.Network.TMDBapirefactor
import com.example.movie_detail.R
import com.example.movie_detail.Room.CrossReference.MovieAndGenreCf
import com.example.movie_detail.Room.CrossReference.MovieAndSimilarMovieCf
import com.example.movie_detail.Room.CrossReference.SimilarMovieWithGenreCf
import com.example.movie_detail.Room.Database
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.example.movie_detail.Room.Entities.Movie.SimilarMovieEntity
import com.example.movie_detail.Room.Relations.MovieWithGenresAndSimilarMovies
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class TMDBRepository(baseUrl: String, private val apiKey: String, val context: Context, val movieId: String) {
    private val TAG = "TMDBRepository"
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
    val movieDetails: LiveData<MovieWithGenresAndSimilarMovies> = database.relations().getMovieDetailsById(movieId);

    suspend fun getMovieDetailsById() = withContext(Dispatchers.IO) {
        val movieDetail = api.loadMovieById(movieId, apiKey).await();
        movieDetail?.apply {
            val movieEntity = MovieEntity.fromApi(this);
            this.genres
                .map { MovieAndGenreCf(movieId, it.genreId.toString()) }
                .apply {
                    database.movieAndGenre().insertManyMovieAndGenreCf(this);
                }
            database.movie().insertOneMovie(movieEntity);
        }
    }

    suspend fun getSimilarlyMoviesOfId() = withContext(Dispatchers.IO) {
        val similarMovieIds = api.loadSimilarMovies(movieId, apiKey).await()?.results;
        val similarMoviesEntity = mutableListOf<SimilarMovieEntity>();

        similarMovieIds?.forEach { similarMovie ->
            val similarMovieWithGenre = mutableListOf<SimilarMovieWithGenreCf>();
            val movie = api.loadMovieById(similarMovie.id, apiKey).await();
            if (movie != null) similarMoviesEntity.add(SimilarMovieEntity.fromApi(movie));
            movie
                ?.genres
                ?.map {
                    SimilarMovieWithGenreCf(movie.movieId, it.genreId)
                }
                .apply {
                    if (this != null) {
                        database.similarMovieAndGenre().insertManyMoviesWithGenresCf(this);
                    }
                }
        }
        similarMoviesEntity
            .map { MovieAndSimilarMovieCf(movieId, it.movieId.toString()) }
            .apply { database.movieAndSimilarMovie().insertManySimilarMovies(this) }

        database.similarMovie().insertManySimilarMovies(similarMoviesEntity);
    }

    suspend fun getSimilarMovieWithGenre() = withContext(Dispatchers.IO) {
        val similarMovieWithGenre = database.relations().getSimilarMovieWithGenreById("627");
        Log.d(TAG, "similar movie => ${similarMovieWithGenre}");
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