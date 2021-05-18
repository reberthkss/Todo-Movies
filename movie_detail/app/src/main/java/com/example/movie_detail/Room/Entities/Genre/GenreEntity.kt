package com.example.movie_detail.Room.Entities.Genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie_detail.Network.Genre.MovieGenreApi


@Entity(tableName = "movie_genres")
data class GenreEntity(
    @PrimaryKey @ColumnInfo(name = "genre_id") val genreId: String,
    @ColumnInfo(name = "genre_name") val genreName: String
) {
    companion object {
        fun fromApi(genreApi: MovieGenreApi): GenreEntity {
            return GenreEntity(genreApi.genreId, genreApi.genreName);
        }
    }
}
