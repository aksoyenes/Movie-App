package com.example.movieaap.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieaap.Data.AppContainer
import com.example.movieaap.model.MovieDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    detailViewModel: MovieDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieId, AppContainer.movieRepository) as T
            }
        }
    )
) {
    val uiState by detailViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.movieDetail?.title ?: "Loading...") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.movieDetail != null -> {
                    MovieDetailContent(movie = uiState.movieDetail!!)
                }
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: MovieDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AsyncImage(
            model = movie.fullPosterUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = String.format("%.1f/10", movie.rating),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Release: ${movie.releaseDate}",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Genres",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(movie.genres) { genre ->
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(text = genre, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.overview,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    }
}