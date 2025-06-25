package com.example.movieswithkajal.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.domain.usecase.GetMovieByIdUseCase
import com.example.movieswithkajal.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    fun loadMovieById(id: Int) {
        viewModelScope.launch {
            _movie.value = getMovieByIdUseCase(id)
        }
    }

    fun toggleBookmark() {
        viewModelScope.launch {
            _movie.value?.let {
                toggleBookmarkUseCase(it)
                loadMovieById(it.id)
            }
        }
    }
}
