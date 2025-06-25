package com.example.movieswithkajal.di

import android.content.Context
import androidx.room.Room
import com.example.movieswithkajal.data.local.dao.MovieDao
import com.example.movieswithkajal.data.local.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "tmdb_movie_db"
        ).build()
    }

    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao = db.movieDao()
}
