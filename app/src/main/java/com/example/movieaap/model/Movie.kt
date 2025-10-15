package com.example.movieaap.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String
){

    val fullPosterUrl: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}