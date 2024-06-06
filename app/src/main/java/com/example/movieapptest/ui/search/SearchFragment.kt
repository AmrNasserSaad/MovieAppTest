package com.example.movieapptest.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapptest.R
import com.example.movieapptest.adapters.MovieSearchAdapter
import com.example.movieapptest.databinding.FragmentSearchBinding
import com.example.movieapptest.databinding.FragmentTopRatedMovieBinding
import com.example.movieapptest.viewModel.SearchMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchMovieViewModel by viewModels()
    private lateinit var adapter: MovieSearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieSearchAdapter()
        binding.rvSearch.layoutManager = GridLayoutManager(context, 2)
        binding.rvSearch.adapter = adapter

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.setSearchQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.onClickListener = {
            val b = Bundle().apply { putParcelable("movie", it) }
            findNavController()
                .navigate(R.id.action_searchFragment_to_movieDetailsFragment, b)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}