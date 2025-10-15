package com.example.movieaap.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object AppContainer {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: TmdbApiService = retrofit.create(TmdbApiService::class.java)

    val movieRepository = MovieRepository(apiService)
}