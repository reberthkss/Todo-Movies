package com.example.movie_detail.Room.Relations

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RelationsDao {
    @Transaction
    @Query("select * from movie")
    fun getMoviesWithGenres(): List<MovieWithGenres>
    @Transaction
    @Query("select * from movie where movie_id like :movieId")
    fun getMovieWithGenreById(movieId: String): MovieWithGenres;
    @Transaction
    @Query("select * from movie")
    fun getMovieWithSimilarMovies(): List<MovieWithSimilarMovies>;
    @Transaction
    @Query("select * from movie where movie_id like :movieId")
    fun getMovieWithSimilarMoviesById( movieId: String): MovieWithSimilarMovies
    @Transaction
    @Query("select * from movie where movie_id ")
    fun getMoviesDetails(): List<MovieWithGenresAndSimilarMovies>
    @Transaction
    @Query ("select * from movie where movie_id like :movieId")
    fun getMovieDetailsById(movieId: String): MovieWithGenresAndSimilarMovies;
}