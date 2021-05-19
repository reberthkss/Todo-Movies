package com.example.movie_detail.Room.CrossReference

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie_and_genre", primaryKeys = ["movie_id", "genre_id"])
data class MovieAndGenreCf(
    @ColumnInfo(name = "movie_id") val movieId: String,
    @ColumnInfo(name = "genre_id") val genreId: String
)
