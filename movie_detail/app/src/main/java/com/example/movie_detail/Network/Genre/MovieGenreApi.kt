package com.example.movie_detail.Network.Genre

import com.squareup.moshi.Json


data class MovieGenreApi(
    @field:Json(name = "id") val genreId: String,
    @field:Json(name = "name") val genreName: String
)
