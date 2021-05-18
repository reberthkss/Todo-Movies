package com.example.movie_detail.Room.CrossReference

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface SimilarMovieWithGenreCfDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneMovieWithGenreCf(movieAndSimilarMovieCf: SimilarMovieWithGenreCf);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyMoviesWithGenresCf(moviesAndSimilarlyMoviesCf: List<SimilarMovieWithGenreCf>);
}