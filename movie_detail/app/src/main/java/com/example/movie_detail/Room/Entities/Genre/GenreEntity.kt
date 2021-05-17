package com.example.movie_detail.Room.Entities.Genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_genres")
data class GenreEntity(
    @PrimaryKey @ColumnInfo(name = "genre_id") val genreId: Long,
    @ColumnInfo(name = "genre_name") val genreName: String
)
