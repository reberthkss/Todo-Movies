package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class GetSimilarMoviesResponse(
    @field:Json(name = "results") val results: List<MovieDetailsDataclasse>
)