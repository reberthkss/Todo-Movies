package com.example.movie_detail.Room

import androidx.room.Database
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Genre.GenreEntityDao
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntityDao

@Database(entities = arrayOf(MovieEntity::class, GenreEntity::class), version = 1)
abstract class Database {
    abstract fun movie(): MovieEntityDao
    abstract fun genre(): GenreEntityDao
}