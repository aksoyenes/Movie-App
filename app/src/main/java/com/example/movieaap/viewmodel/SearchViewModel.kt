package com.example.movieaap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieaap.Data.MovieRepository
import com.example.movieaap.model.Movie
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Arama ekranının anlık durumunu tutacak data class
data class SearchUiState(
    val query: String = "",
    val searchResults: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val infoMessage: String? = "Filmleri aramak için yazmaya başlayın."
)

class SearchViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null

    // Kullanıcı arama kutusuna her harf girdiğinde bu fonksiyon çağrılacak
    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }

        // Kullanıcı yazmayı bıraktıktan kısa bir süre sonra arama yapmak için (debounce)
        searchJob?.cancel() // Önceki arama işini iptal et
        searchJob = viewModelScope.launch {
            delay(500L) // 500 milisaniye bekle
            if (newQuery.isNotBlank()) {
                performSearch(newQuery)
            } else {
                // Arama kutusu boşsa listeyi temizle
                _uiState.update {
                    it.copy(
                        searchResults = emptyList(),
                        infoMessage = "Filmleri aramak için yazmaya başlayın."
                    )
                }
            }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null) }
            val results = repository.searchMovies(query)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    searchResults = results,
                    infoMessage = if (results.isEmpty()) "Sonuç bulunamadı." else null
                )
            }
        }
    }
}