package com.example.movieswithkajal.data.remote

import com.example.movieswithkajal.data.model.TmdbMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("api_key") apiKey: String): TmdbMovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): TmdbMovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): TmdbMovieResponse

}
