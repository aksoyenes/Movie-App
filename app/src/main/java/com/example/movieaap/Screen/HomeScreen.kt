package com.example.movieaap.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieaap.Components.MovieRow
import com.example.movieaap.Data.AppContainer
import com.example.movieaap.Data.dummyNowPlayingMovies
import com.example.movieaap.Data.dummyPopularMovies
import androidx.compose.ui.res.stringResource
import com.example.movieaap.R




val dummyTopRatedMovies = dummyNowPlayingMovies.shuffled()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
    onShowAllClick: (category: String) -> Unit,
    homeViewModel: HomeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(AppContainer.movieRepository) as T
            }
        }
    )
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                    IconButton(onClick = onSettingsClick) { // DÜZELTİLDİ
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.icon_desc_settings)
                        )
                    }
                }

            )
        },
        bottomBar = {

            BottomNavigationBar(onSearchClick = onSearchClick)
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding), // innerPadding'i eklemek iyi bir pratik
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                MovieRow(
                    title = stringResource(R.string.now_playing),
                    movies = uiState.nowPlayingMovies,
                    onShowAllClick = { onShowAllClick("now_playing") },
                    onMovieClick = onMovieClick
                )
                MovieRow(
                    title = stringResource(R.string.popular),
                    movies = uiState.popularMovies,
                    onShowAllClick = { onShowAllClick("popular") },
                    onMovieClick = onMovieClick
                )
                MovieRow(
                    title = stringResource(R.string.top_rated),
                    movies = uiState.topRatedMovies,
                    onShowAllClick = { onShowAllClick("top_rated") },
                    onMovieClick = onMovieClick
                )
                MovieRow(
                    title = stringResource(R.string.upcoming),
                    movies = uiState.upcomingMovies,
                    onShowAllClick = { onShowAllClick("upcoming") },
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(onSearchClick: () -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { /* Zaten ana sayfadayız */ },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(stringResource(R.string.home)) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onSearchClick,
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text(stringResource(R.string.search)) }
        )
    }
}