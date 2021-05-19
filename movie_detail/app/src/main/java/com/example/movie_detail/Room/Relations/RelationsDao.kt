package com.example.movie_detail.Room.Relations

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RelationsDao {
    @Transaction
    @Query("select * from movie")
    fun getMoviesWithGenres(): List<MovieWithGenres>
    @Transaction
    @Query("select * from movie where movie_id like :movieId limit 1")
    fun getMovieWithGenreById(movieId: String): MovieWithGenres;
    @Transaction
    @Query("select * from movie")
    fun getMovieWithSimilarMovies(): List<MovieWithSimilarMovies>;
    @Transaction
    @Query("select * from movie where movie_id like :movieId limit 1")
    fun getMovieWithSimilarMoviesById( movieId: String): MovieWithSimilarMovies
    @Transaction
    @Query("select * from similar_movie ")
    fun getSimilarMoviesWithGenres(): List<SimilarMovieWithGenre>
    @Transaction
    @Query("select * from similar_movie where similar_movie_id like :similarMovieId limit 1")
    fun getSimilarMovieWithGenreById(similarMovieId: String): SimilarMovieWithGenre
    @Transaction
    @Query("select * from similar_movie where similar_movie_id like :similarMovieId")
    fun getSimilarMoviesWithGenreById(similarMovieId: String): List<SimilarMovieWithGenre>
    @Transaction
    @Query("select * from movie where movie_id")
    fun getMoviesDetails(): List<MovieWithGenresAndSimilarMovies>
    @Transaction
    @Query ("select * from movie where movie_id like :movieId limit 1")
    fun getMovieDetailsById(movieId: String): MovieWithGenresAndSimilarMovies

}