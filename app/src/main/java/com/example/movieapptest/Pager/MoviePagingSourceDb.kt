package com.example.movieapptest.Pager

import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapptest.data.local.MovieDao
import com.example.movieapptest.data.remote.Movie

class MoviePagingSourceDb(private val movieDao: MovieDao) : PagingSource<Int, Movie>() {


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1
            val movies = movieDao.getMoviesByPage(pageNumber, 20)
            LoadResult.Page(
                data = movies,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (movies.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}