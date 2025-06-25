package com.example.movieswithkajal.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.movieswithkajal.R
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.presentation.viewmodel.BookmarkViewModel

@Composable
fun BookmarksScreen(viewModel: BookmarkViewModel = hiltViewModel(), onMovieClick: (Int) -> Unit) {
    val bookmarks by viewModel.bookmarkedMovies.collectAsState()

    if (bookmarks.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_bookmarks_yet), color = Color.Gray)
        }
    } else {

        LazyColumn {
            itemsIndexed(bookmarks) { index, movie ->
                BookmarkMovieItem(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onToggleBookmark = { viewModel.toggleBookmark(movie) }
                )

                if (index < bookmarks.lastIndex) {
                    Divider(
                        color = Color.Gray.copy(alpha = 0.3f),
                        thickness = 0.8.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkMovieItem(
    movie: Movie,
    onClick: () -> Unit,
    onToggleBookmark: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = stringResource(R.string.poster),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 80.dp, height = 120.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        IconButton(onClick = onToggleBookmark) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(R.string.remove_bookmark),
                tint = Color.Red
            )
        }
    }
}
