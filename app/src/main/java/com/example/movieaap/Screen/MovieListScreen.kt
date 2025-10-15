package com.example.movieaap.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieaap.Components.MovieItem
import com.example.movieaap.Data.AppContainer
import com.example.movieaap.Data.MovieRepository
import com.example.movieaap.R
import com.example.movieaap.model.Movie
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MovieListViewModelFactory(
    private val repository: MovieRepository,
    private val category: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieListViewModel(repository, category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// ViewModel'i güncelliyoruz
class MovieListViewModel(private val repository: MovieRepository, private val category: String) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    init {

        _uiState.update { it.copy(titleResId = getTitleResIdForCategory(category)) }
        fetchMoviesByCategory()
    }

    private fun fetchMoviesByCategory() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val movies = when (category) {
                    "now_playing" -> repository.getNowPlayingMovies()
                    "popular" -> repository.getPopularMovies()
                    "top_rated" -> repository.getTopRatedMovies()
                    "upcoming" -> repository.getUpcomingMovies()
                    else -> emptyList()
                }
                _uiState.update { it.copy(movies = movies, isLoading = false) }
            } catch (e: Exception) {

                _uiState.update { it.copy(isLoading = false, errorMessageResId = R.string.error_loading_movies) }
            }
        }
    }

    private fun getTitleResIdForCategory(category: String): Int {
        return when (category) {
            "now_playing" -> R.string.now_playing
            "popular" -> R.string.popular
            "top_rated" -> R.string.top_rated
            "upcoming" -> R.string.upcoming
            else -> R.string.movies
        }
    }
}


data class MovieListUiState(
    val movies: List<Movie> = emptyList(),

    val titleResId: Int = R.string.movies,
    val isLoading: Boolean = false,

    val errorMessageResId: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    category: String,
    onMovieClick: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: MovieListViewModel = viewModel(
        factory = MovieListViewModelFactory(AppContainer.movieRepository, category)
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(

                title = { Text(stringResource(id = uiState.titleResId)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {

                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { paddingValues ->

        val currentErrorMessageResId = uiState.errorMessageResId

        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                currentErrorMessageResId != null -> {
                    Text(

                        text = stringResource(id = currentErrorMessageResId),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.movies) { movie ->
                            MovieItem(movie = movie, onClick = { onMovieClick(movie.id) })
                        }
                    }
                }
            }
        }
    }
}