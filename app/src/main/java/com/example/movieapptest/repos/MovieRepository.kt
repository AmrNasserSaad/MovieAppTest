package com.example.movieapptest.repos

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapptest.adapters.MoviePagingSource
import com.example.movieapptest.api.TmdbApi
import com.example.movieapptest.data.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val tmdbApi: TmdbApi) {

    fun getPopularMovies(apiKey: String): Pager<Int, Movie> {
        Log.d("TAG", "repo called")
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(tmdbApi, apiKey) }
        )
    }
}