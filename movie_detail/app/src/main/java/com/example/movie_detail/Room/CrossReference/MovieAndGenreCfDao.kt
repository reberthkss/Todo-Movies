package com.example.movie_detail.Room.CrossReference

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface MovieAndGenreCfDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieAndGenreCf(movieAndGenreCf: MovieAndGenreCf);
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertManyMovieAndGenreCf(movieAndGenreCfs: List<MovieAndGenreCf>);
}