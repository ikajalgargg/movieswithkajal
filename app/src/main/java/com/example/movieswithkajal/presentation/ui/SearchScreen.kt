package com.example.movieswithkajal.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.movieswithkajal.R
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.presentation.viewmodel.SearchViewModel
import com.example.movieswithkajal.utils.Resource

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), onMovieClick: (Int) -> Unit) {

    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.searchResults.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigateToMovieDetail.collect { movieId ->
            onMovieClick(movieId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(Color.DarkGray, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(start = 8.dp)
            )
            TextField(
                value = query,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                placeholder = { Text(stringResource(R.string.search_for_movie), color = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.Red,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorPlaceholderColor = Color.Gray
                ),
                singleLine = true,
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
                        }
                    }
                }
            )
        }

        Spacer(Modifier.height(8.dp))

        when {
            query.isEmpty() -> {
                Text(
                    stringResource(R.string.popular),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            results is Resource.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Red)
                }
            }

            results is Resource.Success -> {
                val movies = (results as Resource.Success<List<Movie>>).data ?: emptyList()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = movies, key = { it.id }) { movie ->
                        MoviePoster(movie = movie, onClick = {
                            viewModel.onSearchClicked(movie)
                        })
                    }
                }
            }

            results is Resource.Error -> {
                Text(
                    text = stringResource(R.string.went_wrong) + (results as Resource.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}