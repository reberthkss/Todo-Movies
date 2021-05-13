package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class MovieDetailsDataclasse(
    @field:Json(name = "vote_count") val voteCount: Int,
    @field:Json(name = "popularity") val popularity: Float,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "poster_path") val imageUrl: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "genres") val genres: List<MovieGenre>
);