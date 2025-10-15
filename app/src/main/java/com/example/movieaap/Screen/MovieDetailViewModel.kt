package com.example.movieaap.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieaap.Data.MovieRepository
import com.example.movieaap.model.MovieDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val error: String? = null
)

class MovieDetailViewModel(
    private val movieId: Int,
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState(isLoading = true))
    val uiState: StateFlow<MovieDetailUiState> = _uiState

    init {
        fetchMovieDetails()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState(isLoading = true)
            val detail = repository.getMovieDetails(movieId)
            if (detail != null) {
                _uiState.value = MovieDetailUiState(isLoading = false, movieDetail = detail)
            } else {
                _uiState.value = MovieDetailUiState(isLoading = false, error = "Failed to load movie details.")
            }
        }
    }
}