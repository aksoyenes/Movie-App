package com.example.movieaap.Data

import com.example.movieaap.model.Movie
import com.example.movieaap.model.MovieDetail


private fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath ?: ""
    )
}


private fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title,
        overview = this.overview ?: "No overview available.",
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate ?: "Unknown",
        rating = this.voteAverage,
        genres = this.genres.map { it.name }
    )
}

class MovieRepository(private val apiService: TmdbApiService) {

    suspend fun getNowPlayingMovies(): List<Movie> {
        return try {
            val response = apiService.getNowPlayingMovies()
            response.results.map { it.toMovie() }
        } catch (e: Exception) {

            emptyList()
        }
    }

    suspend fun searchMovies(query: String): List<Movie> {
        return try {

            val response = apiService.searchMovies(query = query)


            response.results.map { it.toMovie() }
        } catch (e: Exception) {

            println("Arama sırasında hata oluştu: ${e.message}")
            emptyList()
        }
    }

    suspend fun getPopularMovies(): List<Movie> {
        return try {
            val response = apiService.getPopularMovies()
            response.results.map { it.toMovie() }
        } catch (e: Exception) {
            emptyList()
        }
    }


    suspend fun getTopRatedMovies(): List<Movie> {
        return try {
            val response = apiService.getTopRatedMovies()
            response.results.map { it.toMovie() }
        } catch (e: Exception) {
            emptyList()
        }
    }


    suspend fun getMovieDetails(movieId: Int): MovieDetail? {
        return try {
            val response = apiService.getMovieDetails(movieId)
            response.toMovieDetail()
        } catch (e: Exception) {

            e.printStackTrace()
            null
        }
    }

    suspend fun getUpcomingMovies(): List<Movie> {
        return try {
            val response = apiService.getUpcomingMovies()
            response.results.map { it.toMovie() }
        } catch (e: Exception) {
            println("getUpcomingMovies hatası: ${e.message}")
            emptyList()
        }
    }
}