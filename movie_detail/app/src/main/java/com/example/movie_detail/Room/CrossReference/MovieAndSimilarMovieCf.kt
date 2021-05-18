package com.example.movie_detail.Room.CrossReference

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie_and_similar_movie", primaryKeys = ["movie_id", "similar_movie"])
data class MovieAndSimilarMovieCf(
    @ColumnInfo(name = "movie_id") val movieId: String,
    @ColumnInfo(name = "similar_movie") val similarMovie: String
)