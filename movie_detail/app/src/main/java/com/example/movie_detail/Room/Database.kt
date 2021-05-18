package com.example.movie_detail.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movie_detail.Room.CrossReference.MovieAndGenreCf
import com.example.movie_detail.Room.CrossReference.MovieAndGenreCfDao
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Genre.GenreEntityDao
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntityDao
import com.example.movie_detail.Room.Entities.Movie.SimilarEntityDao
import com.example.movie_detail.Room.Relations.RelationsDao

@Database(entities = arrayOf(
    // Entities
    MovieEntity::class, GenreEntity::class,
    // Cross references tables
    MovieAndGenreCf::class
), version = 4)
abstract class Database: RoomDatabase() {
    abstract fun movie(): MovieEntityDao
    abstract fun genre(): GenreEntityDao
    abstract fun similarMovie(): SimilarEntityDao
    abstract fun movieAndGenre(): MovieAndGenreCfDao
    abstract fun relations(): RelationsDao
}