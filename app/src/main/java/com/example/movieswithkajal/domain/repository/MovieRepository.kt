package com.example.movieswithkajal.domain.repository

import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrendingMovies(): Flow<Resource<List<Movie>>>
    fun getNowPlayingMovies(): Flow<Resource<List<Movie>>>
    fun searchMovies(query: String): Flow<Resource<List<Movie>>>
    fun getBookmarkedMovies(): Flow<List<Movie>>
    suspend fun toggleBookmark(movie: Movie)
    suspend fun getMovieById(id: Int): Movie?
    suspend fun insertMovie(movie: Movie)

}
