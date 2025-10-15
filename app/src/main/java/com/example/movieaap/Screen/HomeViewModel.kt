package com.example.movieaap.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieaap.Data.MovieRepository
import com.example.movieaap.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class HomeUiState(
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {

                val nowPlaying = repository.getNowPlayingMovies()
                val popular = repository.getPopularMovies()
                val topRated = repository.getTopRatedMovies()
                val upcoming = repository.getUpcomingMovies()

                _uiState.value = HomeUiState(
                    nowPlayingMovies = nowPlaying,
                    popularMovies = popular,
                    topRatedMovies = topRated,
                    upcomingMovies = upcoming,
                    isLoading = false
                )
            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}