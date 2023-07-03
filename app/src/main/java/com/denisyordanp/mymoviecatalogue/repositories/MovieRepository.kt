package com.denisyordanp.mymoviecatalogue.repositories

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun reloadMovies(genreId: Long)
    fun getMovies(genreId: Long): Flow<List<Movie>>
    fun getMoviesPager(genreId: Long): Flow<PagingData<Movie>>
}
