package com.example.movieaap.Data

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val genres: List<GenreDto>
)