package com.example.movieapptest.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapptest.R
import com.example.movieapptest.databinding.FragmentMovieDetailsBinding
import com.example.movieapptest.databinding.FragmentTopRatedMovieBinding
import com.example.movieapptest.utils.Constants.BASE_IMAGE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val args by navArgs<MovieDetailFragmentArgs>()
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie


        binding.apply {

            Glide.with(this@MovieDetailFragment)
                .load("$BASE_IMAGE_URL${movie.posterPath}")
                .into(movieDetailsImage)

            movieTitle.text = movie.title
            movieVote.text = movie.voteAverage.toString()
            movieOverview.text = movie.overview

            arrowBack.setOnClickListener {
                findNavController().navigate(R.id.action_movieDetailsFragment_to_searchFragment)
            }
        }

    }
}
