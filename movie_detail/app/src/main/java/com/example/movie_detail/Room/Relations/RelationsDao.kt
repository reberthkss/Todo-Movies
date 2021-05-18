package com.example.movie_detail.Room.Relations

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RelationsDao {
    @Query("select * from movie")
    fun getMoviesWithGenres(): List<MovieWithGenres>
    @Query("select * from movie where movie_id like :movieId")
    fun getMovieWithGenreById(movieId: String): MovieWithGenres;
}