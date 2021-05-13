package com.example.movie_detail.Dataclasses

import com.squareup.moshi.Json

data class MovieDetailsDataclasse(
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "popularity") val popularity: Int,
    @Json(name = "title") val title: String
);