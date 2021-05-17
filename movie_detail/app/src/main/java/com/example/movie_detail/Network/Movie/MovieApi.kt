package com.example.movie_detail.Network.Movies

import com.squareup.moshi.Json


data class MovieApi(
    @field:Json(name = "id") val movieId: Long,
    @field:Json(name = "title") val movieTitle: String
    )
