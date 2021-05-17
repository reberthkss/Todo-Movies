package com.example.movie_detail.Room.Entities.Genre

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface GenreEntityDao {
    @Insert
    fun insertOneGenre(genreEntity: GenreEntity);
    @Insert
    fun insertManyGenres(genres: List<GenreEntity>);
}