package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun reloadGenres()
    fun getGenres(): Flow<List<Genre>>
    suspend fun reloadMovies(genreId: Int)
    fun getMovies(genreId: Int): Flow<List<Movie>>
}