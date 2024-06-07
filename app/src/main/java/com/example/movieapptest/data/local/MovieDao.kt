package com.example.movieapptest.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapptest.data.remote.Movie

@Dao
interface MovieDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMovies(movies: List<Movie>)
    @Query("SELECT * FROM movies LIMIT :pageSize OFFSET (:pageNumber - 1) * :pageSize")
    fun getMoviesByPage(pageNumber: Int, pageSize: Int): List<Movie>
    // Other DAO methods
    @Query("SELECT * FROM movies")
    fun getAllMovies(): PagingSource<Int, Movie>


}