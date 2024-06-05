package com.example.movieapptest.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapptest.data.Movie
import com.example.movieapptest.repos.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun getPopularMovies(apiKey: String): Flow<PagingData<Movie>> {

        val movieRepo = movieRepository.getPopularMovies(apiKey).flow.cachedIn(viewModelScope)
        Log.d("TAG", "getPopularMovies: $movieRepo")
        return movieRepo

    }
    fun getTopRatedMovies(apiKey: String): Flow<PagingData<Movie>> {

        val movieRepo = movieRepository.getTopRatedMovies(apiKey).flow.cachedIn(viewModelScope)
        Log.d("TAG", "getPopularMovies: $movieRepo")
        return movieRepo

    }

}