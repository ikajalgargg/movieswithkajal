package com.example.movieswithkajal.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieswithkajal.data.model.Movie

@Composable
fun MoviePoster(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}