package com.example.movie_detail.Room.Relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.movie_detail.Room.CrossReference.SimilarMovieWithGenreCf
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Movie.SimilarMovieEntity

data class SimilarMovieWithGenre(
    @Embedded val similarMovie: SimilarMovieEntity,
    @Relation(
        parentColumn = "similar_movie_id",
        entityColumn = "genre_id",
        associateBy = Junction(SimilarMovieWithGenreCf::class)
    ) val genres: List<GenreEntity>
)
