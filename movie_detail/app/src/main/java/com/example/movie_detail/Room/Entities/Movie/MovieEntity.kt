package com.example.movie_detail.Room.Entities.Movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie_detail.Network.Movie.MovieApi

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "movie_id") val movieId: String,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "image_url") val movieImageUrl: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "vote_count") val voteCount: Long,
    @ColumnInfo(name = "popularity") val popularity: Float,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
) {
    companion object {
        fun fromApi(movieApi: MovieApi): MovieEntity {
            return MovieEntity(
                movieApi.movieId,
                movieApi.movieTitle,
                movieApi.imageUrl,
                movieApi.releaseDate,
                movieApi.voteCount,
                movieApi.popularity
            );
        }
    }
}

@Entity(tableName = "similar_movie")
data class SimilarMovieEntity(
    @PrimaryKey @ColumnInfo(name = "similar_movie_id") val movieId: String,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "image_url") val movieImageUrl: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "vote_count") val voteCount: Long,
    @ColumnInfo(name = "popularity") val popularity: Float,
    @ColumnInfo(name = "is_watched") var isWatched: Boolean = false
) {
    companion object {
        fun fromApi(movieApi: MovieApi): SimilarMovieEntity {
            return SimilarMovieEntity(
                movieApi.movieId,
                movieApi.movieTitle,
                movieApi.imageUrl,
                movieApi.releaseDate,
                movieApi.voteCount,
                movieApi.popularity
            );
        }
    }
}