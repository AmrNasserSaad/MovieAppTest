package com.example.movieapptest.ui.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.adapters.MoviePagingAdapter
import com.example.movieapptest.databinding.FragmentPopularMovieBinding
import com.example.movieapptest.utils.Constants.API_KEY
import com.example.movieapptest.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMovieFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieBinding
    private val viewModel: MovieViewModel by viewModels()
    private val adapter = MoviePagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMovieBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvPopularMovies.layoutManager = GridLayoutManager(context, 2)
        binding.rvPopularMovies.adapter = adapter

        binding.progressBar.visibility = View.GONE

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPopularMovies(API_KEY).collectLatest { pagingData ->
                    Log.d("TAG", "fragment called ")
                    adapter.submitData(pagingData)

                }
            }
        }
    }
}


