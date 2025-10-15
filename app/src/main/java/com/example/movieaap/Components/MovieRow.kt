package com.example.movieaap.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieaap.model.Movie

import androidx.compose.ui.res.stringResource
import com.example.movieaap.R
@Composable
fun MovieRow(
    title: String,
    movies: List<Movie>,
    onShowAllClick: () -> Unit ,
    onMovieClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = onShowAllClick) {
                Text(text = stringResource(R.string.show_all),)
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Show All"
                )
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }
    }
}