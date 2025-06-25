package com.example.movieswithkajal.data.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val posterUrl: String,
    val releaseDate: String,
    val isBookmarked: Boolean = false
)
