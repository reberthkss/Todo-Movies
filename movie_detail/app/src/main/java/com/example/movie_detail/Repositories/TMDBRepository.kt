package com.example.movie_detail.Repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.movie_detail.Network.TMDBApi
import com.example.movie_detail.Room.CrossReference.MovieAndGenreCf
import com.example.movie_detail.Room.CrossReference.MovieAndSimilarMovieCf
import com.example.movie_detail.Room.CrossReference.SimilarMovieWithGenreCf
import com.example.movie_detail.Room.Database
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.example.movie_detail.Room.Entities.Movie.SimilarMovieEntity
import com.example.movie_detail.Room.Relations.MovieWithGenres
import com.example.movie_detail.Room.Relations.MovieWithGenresAndSimilarMovies
import com.example.movie_detail.Room.Relations.MovieWithSimilarMovies
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
            .create(TMDBApi::class.java)
    }
    private val database by lazy {
        Room
            .databaseBuilder(context, Database::class.java, "reberth_app")
            .fallbackToDestructiveMigration()
            .build()
    }

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

    suspend fun getMovieWithGenre() = withContext(Dispatchers.IO) {
        val movieWithGenre: MovieWithGenres = database.relations().getMovieWithGenreById(movieId);
        movieWithGenre
    }

    suspend fun getSimilarMovieWithGenre() = withContext(Dispatchers.IO) {
        val similarMovieWithGenre: MovieWithSimilarMovies = database.relations().getMovieWithSimilarMoviesById(movieId)
        similarMovieWithGenre
    }

    suspend fun updateMovieEntity(movie: MovieEntity) = withContext(Dispatchers.IO) {
        database.movie().updateMovieEntity(movie);
    }

    suspend fun updateSimilarMovieEntity(similarMovie: SimilarMovieEntity) = withContext(Dispatchers.IO) {
        database.similarMovie().updateSimilarMovieEntity(similarMovie);
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