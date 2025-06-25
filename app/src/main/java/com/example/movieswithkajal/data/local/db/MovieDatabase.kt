package com.example.movieswithkajal.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieswithkajal.data.local.dao.MovieDao
import com.example.movieswithkajal.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
