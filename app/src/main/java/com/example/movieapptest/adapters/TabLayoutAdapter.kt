package com.example.movieapptest.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapptest.ui.movie.fragments.PopularMovieFragment
import com.example.movieapptest.ui.movie.fragments.TopRatedMovieFragment

internal class TabLayoutAdapter(
    fragmentActivity: FragmentActivity,
    private val totalTabs: Int
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = totalTabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularMovieFragment()
            1 -> TopRatedMovieFragment()
            else -> throw IllegalStateException("Invalid tab position")
        }
    }
}