package com.example.movieswithkajal.data.model

import com.example.movieswithkajal.data.local.entity.MovieEntity

fun TmdbMovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title ?: "Unknown",
        description = overview ?: "No description",
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
        releaseDate = releaseDate ?: "N/A",
        isBookmarked = false
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id, title, description, posterUrl, releaseDate, isBookmarked
    )
}

fun Movie.toEntity(isBookmarked: Boolean = false, category: String = ""): MovieEntity {
    return MovieEntity(
        id,
        title,
        description,
        posterUrl,
        releaseDate,
        isBookmarked,
        category
    )
}
