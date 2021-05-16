package com.example.movie_detail.Dataclasses

import com.example.movie_detail.R
import com.squareup.moshi.Json
import kotlin.coroutines.coroutineContext

data class TMDBImagesConfig(
    @field:Json(name = "secure_base_url") val baseUrl: String? = null
)