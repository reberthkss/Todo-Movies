package com.example.movie_detail.Dataclasses

data class SimpleMovieData(
    var movieDetails: MovieDetailsDataclasse? = null,
    var similarMovies: List<MovieDetailsDataclasse>? = null
)