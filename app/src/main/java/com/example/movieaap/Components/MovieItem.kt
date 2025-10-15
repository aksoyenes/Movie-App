package com.example.movieaap.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieaap.model.Movie

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier , onClick: () -> Unit) {
    Column(
        modifier = modifier
            .width(150.dp)
            .padding(8.dp).clickable(onClick = onClick),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Film afişi
        AsyncImage(
            model = movie.fullPosterUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        IconButton(onClick = { /* TODO: Beğenme işlevi eklenecek */ }) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Add to Favorites"
            )
        }
    }
}