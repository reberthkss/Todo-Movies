package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class TMDBResourceConfig(
    @field:Json(name = "images") val images: TMDBImagesConfig
)