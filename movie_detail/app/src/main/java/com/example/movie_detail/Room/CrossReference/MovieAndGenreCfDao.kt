package com.example.movie_detail.Room.CrossReference

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
@Dao
interface MovieAndGenreCfDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieAndGenreCf(movieAndGenreCf: MovieAndGenreCf);
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertManyMovieAndGenreCf(movieAndGenreCfs: List<MovieAndGenreCf>);
}