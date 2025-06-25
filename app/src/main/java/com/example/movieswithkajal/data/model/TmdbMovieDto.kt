package com.example.movieswithkajal.data.model

import com.google.gson.annotations.SerializedName

data class TmdbMovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?
)

data class TmdbMovieResponse(
    @SerializedName("results") val results: List<TmdbMovieDto>
)
