package com.example.movieapptest.repos

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapptest.Pager.PopularMoviePagingSource
import com.example.movieapptest.Pager.SearchMoviePagingSource
import com.example.movieapptest.Pager.TopRatedMoviePagingSource
import com.example.movieapptest.api.TmdbApi
import com.example.movieapptest.data.local.MovieDao
import com.example.movieapptest.data.remote.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val tmdbApi: TmdbApi,
    private val movieDao: MovieDao

    ) :MovieRepositoryInterface {

    fun getPopularMovies(apiKey: String): Flow<PagingData<Movie>>  {
        Log.d("TAG", "repo called")
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { PopularMoviePagingSource(tmdbApi, apiKey) }
        ).flow
    }

    fun getTopRatedMovies(apiKey: String): Flow<PagingData<Movie>> {
        Log.d("TAG", "repo called")
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { TopRatedMoviePagingSource(tmdbApi, apiKey) }
        ).flow
    }


    fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { SearchMoviePagingSource(tmdbApi, query) }
        ).flow
    }



}