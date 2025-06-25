package com.example.movieswithkajal.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieswithkajal.R
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val trending by homeViewModel.trending.collectAsState()
    val nowPlaying by homeViewModel.nowPlaying.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            item { ShimmerMovieRow(stringResource(R.string.trending)) }
            item { ShimmerMovieRow(stringResource(R.string.now_playing)) }
        } else {
            trending.data?.let {
                item { MovieRow(stringResource(R.string.trending), it, onMovieClick) }
            }
            nowPlaying.data?.let {
                item { MovieRow(stringResource(R.string.now_playing), it, onMovieClick) }
            }
        }
    }
}