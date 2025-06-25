package com.example.movieswithkajal.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.domain.usecase.GetBookmarkedMoviesUseCase
import com.example.movieswithkajal.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    val bookmarkedMovies = getBookmarkedMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleBookmark(movie: Movie) {
        viewModelScope.launch {
            toggleBookmarkUseCase(movie)
        }
    }
}
