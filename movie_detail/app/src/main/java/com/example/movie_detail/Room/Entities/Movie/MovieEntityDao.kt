package com.example.movie_detail.Room.Entities.Movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MovieEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneMovie(movieEntity: MovieEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyMovies(movies: List<MovieEntity>);
}

@Dao
interface SimilarEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneSimilarMovie(movieEntity: SimilarMovieEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManySimilarMovies(movies: List<SimilarMovieEntity>);
}