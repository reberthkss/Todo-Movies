package com.example.movie_detail.Network.Movie

import com.example.movie_detail.Network.Genre.MovieGenreApi
import com.squareup.moshi.Json


data class MovieApi(
    @field:Json(name = "id") val movieId: String,
    @field:Json(name = "title") val movieTitle: String,
    @field:Json(name = "genres") val genres: List<MovieGenreApi>,
    @field:Json(name = "poster_path") val imageUrl: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "vote_count") val voteCount: Long,
    @field:Json(name = "popularity") val popularity: Float
    )
