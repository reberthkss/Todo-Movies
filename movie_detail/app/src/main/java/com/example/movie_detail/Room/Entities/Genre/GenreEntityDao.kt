package com.example.movie_detail.Room.Entities.Genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface GenreEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneGenre(genreEntity: GenreEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyGenres(genres: List<GenreEntity>);
}