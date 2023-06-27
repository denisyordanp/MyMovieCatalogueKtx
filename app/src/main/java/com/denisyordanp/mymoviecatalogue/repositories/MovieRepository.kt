package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun reloadMovies(genreId: Int)
    fun getMovies(genreId: Int): Flow<List<Movie>>
}
