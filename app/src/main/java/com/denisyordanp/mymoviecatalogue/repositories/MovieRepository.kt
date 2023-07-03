package com.denisyordanp.mymoviecatalogue.repositories

import androidx.paging.Pager
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie as DbMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun reloadMovies(genreId: Long)
    fun getMovies(genreId: Long): Flow<List<Movie>>
    fun getMoviesPager(genreId: Long): Pager<Int, DbMovie>
}
