package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class MovieGenre(
    @field:Json(name = "name") val name: String
)