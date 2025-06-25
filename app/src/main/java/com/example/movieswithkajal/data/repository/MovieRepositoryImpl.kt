package com.example.movieswithkajal.data.repository

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.movieswithkajal.BuildConfig
import com.example.movieswithkajal.data.local.dao.MovieDao
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.data.model.toDomain
import com.example.movieswithkajal.data.model.toEntity
import com.example.movieswithkajal.data.remote.TmdbApiService
import com.example.movieswithkajal.domain.repository.MovieRepository
import com.example.movieswithkajal.utils.Resource
import com.example.movieswithkajal.utils.isConnectedToInternet
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApiService,
    private val dao: MovieDao,
    @ApplicationContext private val context: Context

) : MovieRepository {
    val apiKey = BuildConfig.TMDB_API_KEY

    override fun getTrendingMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        if (context.isConnectedToInternet()) {
            try {
                val response = api.getTrendingMovies(apiKey)
                val movies = response.results.map { it.toDomain() }
                dao.clearAll()
                dao.insertAll(movies.map { it.toEntity(category = "trending") })
            } catch (e: Exception) {
                emit(Resource.Error("Network error: ${e.message}"))
            }
        }
        emitAll(
            dao.getMoviesByCategory("trending")
                .map { Resource.Success(it.map { entity -> entity.toDomain() }) }
        )
    }

    override fun getNowPlayingMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        if (context.isConnectedToInternet()) {
            try {
                val response = api.getNowPlayingMovies(apiKey)
                val movies = response.results.map { it.toDomain() }
                dao.insertAll(movies.map { it.toEntity(category = "now playing") })
            } catch (e: Exception) {
                emit(Resource.Error("Network error: ${e.message}"))
            }
        }
        emitAll(
            dao.getMoviesByCategory(category = "now playing")
                .map { Resource.Success(it.map { entity -> entity.toDomain() }) }
        )
    }

    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        if (context.isConnectedToInternet()) {
            try {
                val response = api.searchMovies(query, apiKey)
                val movies = response.results.map { it.toDomain() }
                // Save results into Room
                dao.insertAll(movies.map { it.toEntity() })
                emit(Resource.Success(movies))

            } catch (e: Exception) {
                emit(Resource.Error("Search failed: ${e.message}"))
            }
        }
    }

    override fun getBookmarkedMovies(): Flow<List<Movie>> {
        return dao.getBookmarkedMovies().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getMovieById(id: Int): Movie? {
        return dao.getMovieById(id)?.toDomain()
    }

    override suspend fun toggleBookmark(movie: Movie) {
        val current = dao.getMovieById(movie.id)
        if (current != null) {
            val updated = current.copy(isBookmarked = !current.isBookmarked)
            dao.updateMovie(updated)
        } else {
            dao.insertAll(listOf(movie.toEntity(isBookmarked = true)))
        }
    }

    override suspend fun insertMovie(movie: Movie) {
        dao.insertAll(listOf(movie.toEntity(category = "search")))
    }

}
