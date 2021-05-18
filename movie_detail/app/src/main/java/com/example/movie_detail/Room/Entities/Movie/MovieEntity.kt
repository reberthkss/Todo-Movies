package com.example.movie_detail.Room.Entities.Movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie_detail.Network.Movie.MovieApi

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "movie_id") val movieId: String,
    @ColumnInfo(name = "movie_title") val movieTitle: String
) {
    companion object {
        fun fromApi(movieApi: MovieApi): MovieEntity {
            return MovieEntity(movieApi.movieId, movieApi.movieTitle);
        }
    }
}

@Entity(tableName = "similar_movie")
data class SimilarMovieEntity(
    @PrimaryKey @ColumnInfo(name = "similar_movie_id") val movieId: String,
    @ColumnInfo(name = "movie_title") val movieTitle: String
) {
    companion object {
        fun fromApi(movieApi: MovieApi): SimilarMovieEntity {
            return SimilarMovieEntity(movieApi.movieId, movieApi.movieTitle);
        }
    }
}