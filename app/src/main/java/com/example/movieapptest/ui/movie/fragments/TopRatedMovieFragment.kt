package com.example.movieapptest.ui.movie.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapptest.R
import com.example.movieapptest.adapters.MoviePagingAdapter
import com.example.movieapptest.databinding.FragmentPopularMovieBinding
import com.example.movieapptest.databinding.FragmentTopRatedMovieBinding
import com.example.movieapptest.utils.Constants
import com.example.movieapptest.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedMovieFragment : Fragment() {

    private lateinit var binding: FragmentTopRatedMovieBinding
    private val viewModel: MovieViewModel by viewModels()
    private val adapter = MoviePagingAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopRatedMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvTopRatedMovie.layoutManager = GridLayoutManager(context, 2)
        binding.rvTopRatedMovie.adapter = adapter



        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTopRatedMovies(Constants.API_KEY).collectLatest { pagingData ->
                    Log.d("TAG", "fragment called ")
                    adapter.submitData(pagingData)

                }
            }
        }
    }


}