package com.example.movieaap.Screen

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieaap.Components.MovieItem
import com.example.movieaap.Data.AppContainer
import com.example.movieaap.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    searchViewModel: SearchViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(AppContainer.movieRepository) as T
            }
        }
    )
) {
    val uiState by searchViewModel.uiState.collectAsState()


    val currentInfoMessage = uiState.infoMessage


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Film Ara") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {

            OutlinedTextField(
                value = uiState.query,
                onValueChange = { searchViewModel.onQueryChange(it) },
                label = { Text("Film adı...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )


            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                currentInfoMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = currentInfoMessage)
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(uiState.searchResults) { movie ->
                            MovieItem(movie = movie, onClick = { onMovieClick(movie.id) })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}