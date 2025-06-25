package com.example.movieswithkajal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movieswithkajal.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isBookmarked = 1")
    fun getBookmarkedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE category = :category ORDER BY releaseDate DESC")
    fun getMoviesByCategory(category: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
