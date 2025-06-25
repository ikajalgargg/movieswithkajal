package com.example.movieswithkajal.di

import com.example.movieswithkajal.BuildConfig
import com.example.movieswithkajal.data.remote.TmdbApiService
import com.example.movieswithkajal.utils.ApiConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val TMDB_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YTI0MGViZTM5NThlMjhiMTI2ZjI1NWMzYjUzMjgzMiIsIm5iZiI6MTc1MDYwNzA3Ni43MTgwMDAyLCJzdWIiOiI2ODU4MjRlNGZjNGM5NjlmOWRhNDY2NTEiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.aJeNQzQdvocLvBXmXzTa-P2cJSPkzZxzF2BNIRHO-DM"

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${TMDB_TOKEN}")
                .build()
            chain.proceed(request)
        }.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService =
        retrofit.create(TmdbApiService::class.java)
}
