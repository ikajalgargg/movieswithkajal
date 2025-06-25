package com.example.movieswithkajal.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.domain.usecase.SaveMovieUseCase
import com.example.movieswithkajal.domain.usecase.SearchMoviesUseCase
import com.example.movieswithkajal.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val saveMovieUseCase: SaveMovieUseCase
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<Resource<List<Movie>>?>(null)
    val searchResults = _searchResults.asStateFlow()

    private val _navigateToMovieDetail = MutableSharedFlow<Int>()
    val navigateToMovieDetail = _navigateToMovieDetail.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            _searchResults.value = null
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L) // debounce
            searchMoviesUseCase(query).collect {
                _searchResults.value = it
            }
        }
    }

    fun onSearchClicked(movie: Movie) {
        viewModelScope.launch {
            saveMovieUseCase(movie)
            _navigateToMovieDetail.emit(movie.id)
        }
    }
}