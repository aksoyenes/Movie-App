package com.example.movieaap.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val rating: Double,
    val genres: List<String>
) {
    val fullPosterUrl: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}