package com.example.movieswithkajal.domain.usecase


import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.domain.repository.MovieRepository
import com.example.movieswithkajal.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Movie>>> = repository.searchMovies(query)
}
