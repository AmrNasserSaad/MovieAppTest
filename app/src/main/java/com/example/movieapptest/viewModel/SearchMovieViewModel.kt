package com.example.movieapptest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieapptest.data.remote.Movie
import com.example.movieapptest.repos.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val movies: StateFlow<PagingData<Movie>> = searchQuery
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            repository.searchMovies(query)
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}