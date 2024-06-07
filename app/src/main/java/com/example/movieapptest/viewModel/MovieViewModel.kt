package com.example.movieapptest.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapptest.data.Movie
import com.example.movieapptest.repos.MovieRepository
import com.example.movieapptest.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PagingData<Movie>>>(UiState.Loading)
    val uiState: StateFlow<UiState<PagingData<Movie>>> = _uiState

    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            movieRepository.getPopularMovies(apiKey)
                .cachedIn(viewModelScope)
                .onStart {
                    Log.e("TAG", "Loading viewmodel")
                    delay(500)
                    _uiState.value = UiState.Loading
                }
                .catch { exception ->
                    Log.e("TAG", "Error viewmodel: ${exception.message}")
                    _uiState.value = UiState.Error(exception.message ?: "Unknown Error")
                }
                .collectLatest { pagingData ->
                    Log.e("TAG", "Success viewmodel")
                    _uiState.value = UiState.Success(pagingData)
                }
        }
    }
    fun getTopRatedMovies(apiKey: String) {
        viewModelScope.launch {
            movieRepository.getTopRatedMovies(apiKey)
                .cachedIn(viewModelScope)
                .onStart {
                    Log.e("TAG", "Loading viewmodel")
                    delay(500)
                    _uiState.value = UiState.Loading
                }
                .catch { exception ->
                    Log.e("TAG", "Error viewmodel: ${exception.message}")
                    _uiState.value = UiState.Error(exception.message ?: "Unknown Error")
                }
                .collectLatest { pagingData ->
                    Log.e("TAG", "Success viewmodel")
                    _uiState.value = UiState.Success(pagingData)
                }
        }
    }

//        val movieRepo = movieRepository.getPopularMovies(apiKey).flow.cachedIn(viewModelScope)
//        Log.d("TAG", "getPopularMovies: $movieRepo")
//        return movieRepo


    }


//    fun getTopRatedMovies(apiKey: String): Flow<PagingData<Movie>> {
//
//        return movieRepository.getTopRatedMovies(apiKey)
//            .flow
//            .cachedIn(viewModelScope)
//            .onStart { _uiState.value = UiState.Loading }
//            .catch { exception ->
//                _uiState.value = UiState.Error(exception.message ?: "Unknown Error")
//            }
//            .onCompletion { _uiState.value = UiState.Success }
//
//        //        val movieRepo = movieRepository.getTopRatedMovies(apiKey).flow.cachedIn(viewModelScope)
////        Log.d("TAG", "getTopRatedMovies: $movieRepo")
////        return movieRepo
//
//    }
