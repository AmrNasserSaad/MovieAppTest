package com.example.movieapptest.Pager

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapptest.api.TmdbApi
import com.example.movieapptest.data.remote.Movie
import com.example.movieapptest.utils.Constants.API_KEY

class SearchMoviePagingSource (
    private val tmdbApi: TmdbApi,
    private val query: String,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Log.d("TAG", "pager called")
        return try {
            val page = params.key ?: 1 // Start with page 1 if no key is provided
            val response = tmdbApi.searchMovies(API_KEY,query, page)
            val movies = response.results?.filterNotNull() ?: emptyList()

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1, // Only allow going back if not on the first page
                nextKey = if (page == response.totalPages) null else page + 1 // Stop paging when you reach the last page
            )
        } catch (e: Exception) {
            LoadResult.Error(e) // Handle errors gracefully
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}