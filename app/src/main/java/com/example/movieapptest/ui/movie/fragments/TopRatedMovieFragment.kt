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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapptest.R
import com.example.movieapptest.adapters.MoviePagingAdapter
import com.example.movieapptest.databinding.FragmentTopRatedMovieBinding
import com.example.movieapptest.uistate.UiState
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


      setupRecyclerView()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    Log.e("TAG", "State collected: $state")
                    when (state) {
                        is UiState.Loading -> {
                            Log.e("TAG", "Loading:Fragment")
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvTopRatedMovie.visibility = View.GONE
                            binding.errorMessage.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            Log.e("TAG", "Success: Fragment")
                            binding.progressBar.visibility = View.GONE
                            binding.rvTopRatedMovie.visibility = View.VISIBLE
                            binding.errorMessage.visibility = View.GONE
                            adapter.submitData(lifecycle, state.data)
                        }
                        is UiState.Error -> {
                            Log.e("TAG", "Error: Fragment")
                            binding.progressBar.visibility = View.GONE
                            binding.rvTopRatedMovie.visibility = View.GONE
                            binding.errorMessage.visibility = View.VISIBLE
                            binding.errorMessage.text = state.message
                        }
                        else -> Unit
                    }
                }
            }
        }


        // Trigger the initial load
        viewModel.getTopRatedMovies(Constants.API_KEY)


        navigateToDetailsScreen()

    }

    private fun navigateToDetailsScreen() {
        adapter.onClickListener = {
            val b = Bundle().apply { putParcelable("movie", it) }
            findNavController()
                .navigate(R.id.action_homeFragment_to_movieDetailsFragment, b)
        }
    }

    private fun setupRecyclerView() {
        binding.rvTopRatedMovie.layoutManager = GridLayoutManager(context, 2)
        binding.rvTopRatedMovie.adapter = adapter
    }

}