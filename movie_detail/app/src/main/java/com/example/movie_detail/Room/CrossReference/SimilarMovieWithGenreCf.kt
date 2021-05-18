package com.example.movie_detail.Room.CrossReference

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "similar_movie_with_genre", primaryKeys = ["similar_movie_id", "genre_id"])
data class SimilarMovieWithGenreCf(
    @ColumnInfo(name = "similar_movie_id") val movieId: String,
    @ColumnInfo(name = "genre_id") val genreId: String
)
