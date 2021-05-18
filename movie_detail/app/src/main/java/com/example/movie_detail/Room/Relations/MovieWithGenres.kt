package com.example.movie_detail.Room.Relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.movie_detail.Room.CrossReference.MovieAndGenreCf
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntity

data class MovieWithGenres(
    @Embedded val movie: MovieEntity,
    @Relation
        (parentColumn = "movie_id", entityColumn = "genre_id", associateBy = Junction(MovieAndGenreCf::class))
    val genres: List<GenreEntity>
)
