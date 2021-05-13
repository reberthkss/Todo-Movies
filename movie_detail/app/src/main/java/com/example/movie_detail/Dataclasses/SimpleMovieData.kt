package com.example.movie_detail.Dataclasses

data class SimpleMovieData(
    var movieDetails: MovieDetailsDataclasse,
    var similarMovies: List<MovieDetailsDataclasse>
)