package com.example.movieapptest.api


import com.example.movieapptest.data.PopularMovieResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): PopularMovieResponse
}