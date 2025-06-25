package com.example.movieswithkajal.domain.usecase


import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.domain.repository.MovieRepository
import com.example.movieswithkajal.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.toggleBookmark(movie)
}
