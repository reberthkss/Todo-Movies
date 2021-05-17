package com.example.movie_detail.Room.Entities.Movie

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MovieEntityDao {
    @Insert
    fun insertOneMovie(movieEntity: MovieEntity);
    @Insert
    fun insertManyMovies(movies: List<MovieEntity>);
}