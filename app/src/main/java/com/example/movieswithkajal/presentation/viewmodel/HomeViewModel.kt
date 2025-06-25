package com.example.movieswithkajal.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.movieswithkajal.domain.usecase.GetTrendingMoviesUseCase
import com.example.movieswithkajal.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMovies: GetTrendingMoviesUseCase,
    private val getNowPlayingMovies: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _trending = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val trending: StateFlow<Resource<List<Movie>>> = _trending

    private val _nowPlaying = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val nowPlaying: StateFlow<Resource<List<Movie>>> = _nowPlaying

    val isLoading = combine(_trending, _nowPlaying) { t, n ->
        t is Resource.Loading || n is Resource.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    init {
        fetchTrending()
        fetchNowPlaying()
    }

    private fun fetchTrending() {
        viewModelScope.launch(Dispatchers.IO) {
            getTrendingMovies().collect { _trending.value = it }
        }
    }

    private fun fetchNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            getNowPlayingMovies().collect { _nowPlaying.value = it }
        }
    }
}