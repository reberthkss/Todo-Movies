package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class SimilarMovieDataclasse(
    @field:Json(name = "genre_ids") val genreIds: List<String>
)