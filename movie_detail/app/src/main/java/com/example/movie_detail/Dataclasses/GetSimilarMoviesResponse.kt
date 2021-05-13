package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class GetSimilarMoviesResponse(
    @Json(name = "results") val similarMovies: List<MovieDetailsDataclasse>
)