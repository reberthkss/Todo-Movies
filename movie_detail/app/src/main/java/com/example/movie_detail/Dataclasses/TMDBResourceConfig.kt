package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class TMDBResourceConfig(
    @field:Json(name = "secure_base_url") val baseUrl: String
)