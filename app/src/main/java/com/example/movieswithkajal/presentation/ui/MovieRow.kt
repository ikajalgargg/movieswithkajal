package com.example.movieswithkajal.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieswithkajal.data.model.Movie

@Composable
fun MovieRow(title: String, movies: List<Movie>, onMovieClick: (Movie) -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(
                items = movies,
                key = { it.id }
            ) { movie ->
                MoviePoster(movie = movie, onClick = { onMovieClick(movie) })
            }
        }
    }
}