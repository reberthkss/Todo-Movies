package com.example.movie_detail.Room.Relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.movie_detail.Room.CrossReference.MovieAndGenreCf
import com.example.movie_detail.Room.CrossReference.MovieAndSimilarMovieCf
import com.example.movie_detail.Room.Entities.Genre.GenreEntity
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.example.movie_detail.Room.Entities.Movie.SimilarMovieEntity

data class MovieWithGenresAndSimilarMovies (
    @Embedded val movie: MovieEntity,
    @Relation(parentColumn = "movie_id", entityColumn = "genre_id", associateBy = Junction(MovieAndGenreCf::class))
    val genres: List<GenreEntity>,
    @Relation(entity = SimilarMovieEntity::class, parentColumn = "movie_id", entityColumn = "similar_movie_id", associateBy = Junction(MovieAndSimilarMovieCf::class))
    val similarMovies: List<SimilarMovieWithGenre>
    )