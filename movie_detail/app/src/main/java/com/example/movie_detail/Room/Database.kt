package com.example.movie_detail.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movie_detail.Room.CrossReference.*
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Genre.GenreEntityDao
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntityDao
import com.example.movie_detail.Room.Entities.Movie.SimilarEntityDao
import com.example.movie_detail.Room.Entities.Movie.SimilarMovieEntity
import com.example.movie_detail.Room.Relations.RelationsDao

@Database(entities = arrayOf(
    // Entities
    MovieEntity::class, GenreEntity::class, SimilarMovieEntity::class,
    // Cross references tables
    MovieAndGenreCf::class, MovieAndSimilarMovieCf::class, SimilarMovieWithGenreCf::class
), version = 15)
abstract class Database: RoomDatabase() {
    abstract fun movie(): MovieEntityDao
    abstract fun genre(): GenreEntityDao
    abstract fun similarMovie(): SimilarEntityDao
    abstract fun movieAndGenre(): MovieAndGenreCfDao
    abstract fun movieAndSimilarMovie(): MovieAndSimilarMovieDao
    abstract fun similarMovieAndGenre(): SimilarMovieWithGenreCfDao
    abstract fun relations(): RelationsDao
}