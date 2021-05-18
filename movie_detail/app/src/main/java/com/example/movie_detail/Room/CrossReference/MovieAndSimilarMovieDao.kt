package com.example.movie_detail.Room.CrossReference

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.movie_detail.Room.Entities.Movie.SimilarMovieEntity

@Dao
interface MovieAndSimilarMovieDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertOneSimilarMovie(similarMovie: MovieAndSimilarMovieCf);
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertManySimilarMovies(similarMovies: List<MovieAndSimilarMovieCf>);
}