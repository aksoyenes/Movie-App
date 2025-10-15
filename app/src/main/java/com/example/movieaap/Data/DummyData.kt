package com.example.movieaap.Data

import com.example.movieaap.model.Movie


val dummyNowPlayingMovies = listOf(
    Movie(1, "Red One", "https://image.tmdb.org/t/p/w500/wVJG3v5K4Dk3eX3B2i43w22B6s6.jpg"),
    Movie(2, "Mufasa", "https://image.tmdb.org/t/p/w500/8kRqrwA5zU41y3DB72eTso0p5Ty.jpg"),
    Movie(3, "Moana 2", "https://image.tmdb.org/t/p/w500/6oH378yE56K1oHfTfnbgL0zT0Jk.jpg"),
    Movie(4, "Başka Film", "https://image.tmdb.org/t/p/w500/wVJG3v5K4Dk3eX3B2i43w22B6s6.jpg"),
)

val dummyPopularMovies = listOf(
    Movie(5, "Red One", "https://image.tmdb.org/t/p/w500/wVJG3v5K4Dk3eX3B2i43w22B6s6.jpg"),
    Movie(6, "Venom", "https://image.tmdb.org/t/p/w500/s2StGq1m22nZtapoXqM58z5iYdp.jpg"),
    Movie(7, "Mufasa", "https://image.tmdb.org/t/p/w500/8kRqrwA5zU41y3DB72eTso0p5Ty.jpg"),
    Movie(8, "Başka Popüler", "https://image.tmdb.org/t/p/w500/s2StGq1m22nZtapoXqM58z5iYdp.jpg"),
)

val dummyTopRatedMovies = dummyPopularMovies.shuffled() // Diğer listelerden rastgele oluşturabiliriz