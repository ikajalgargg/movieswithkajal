package com.example.movieswithkajal.domain.usecase

import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.domain.repository.MovieRepository
import javax.inject.Inject

class SaveMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.insertMovie(movie)
    }
}
