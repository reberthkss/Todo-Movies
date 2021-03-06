package com.example.movie_detail.Network.Responses

import com.example.movie_detail.Network.Genre.MovieGenreApi
import com.squareup.moshi.Json

data class GetGenres(
    @field:Json(name = "genres") val genres: List<MovieGenreApi>
)
