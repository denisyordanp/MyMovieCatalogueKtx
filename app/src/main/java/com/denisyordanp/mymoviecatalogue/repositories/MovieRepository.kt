package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun reloadMovies(genreId: Long)
    fun getMovies(genreId: Long): Flow<List<Movie>>
}
