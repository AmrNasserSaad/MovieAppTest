package com.example.movieapptest.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapptest.data.remote.Movie
import com.example.movieapptest.databinding.MovieRvItemBinding
import com.example.movieapptest.utils.Constants.BASE_IMAGE_URL

class MoviePagingAdapter :
    PagingDataAdapter<Movie, MoviePagingAdapter
    .MovieViewHolder>(MovieDiffCallback()) {

    class MovieViewHolder(private val binding: MovieRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {

            binding.apply {

                title.text = movie.title
                vote.text = movie.voteAverage.toString()

                Glide.with(itemView.context)
                    .load("$BASE_IMAGE_URL${movie.posterPath}")
                    .into(movieImage)

            }
            Log.d("TAG", "Adapter called")
        }
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                onClickListener?.invoke(movie)
            }
        }



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MovieRvItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }


    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }


    var onClickListener: ((Movie) -> Unit)? = null

}