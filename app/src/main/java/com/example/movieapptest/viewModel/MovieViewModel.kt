package com.example.movieapptest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapptest.data.Movie
import com.example.movieapptest.repos.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    fun getPopularMovies(apiKey: String): Flow<PagingData<Movie>> {

        return movieRepository.getPopularMovies(apiKey)
            .flow
            .cachedIn(viewModelScope)
            .onStart { _uiState.value = UIState.Loading }
            .catch { exception ->
                _uiState.value = UIState.Error(exception.message ?: "Unknown Error")
            }
            .onCompletion { _uiState.value = UIState.Success }


//        val movieRepo = movieRepository.getPopularMovies(apiKey).flow.cachedIn(viewModelScope)
//        Log.d("TAG", "getPopularMovies: $movieRepo")
//        return movieRepo

    }

    fun getTopRatedMovies(apiKey: String): Flow<PagingData<Movie>> {

        return movieRepository.getTopRatedMovies(apiKey)
            .flow
            .cachedIn(viewModelScope)
            .onStart { _uiState.value = UIState.Loading }
            .catch { exception ->
                _uiState.value = UIState.Error(exception.message ?: "Unknown Error")
            }
            .onCompletion { _uiState.value = UIState.Success }

        //        val movieRepo = movieRepository.getTopRatedMovies(apiKey).flow.cachedIn(viewModelScope)
//        Log.d("TAG", "getTopRatedMovies: $movieRepo")
//        return movieRepo

    }


    sealed class UIState {
        object Loading : UIState()
        data class Error(val message: String) : UIState()
        object Success : UIState()
    }
}