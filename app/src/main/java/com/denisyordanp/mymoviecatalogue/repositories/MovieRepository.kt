package com.denisyordanp.mymoviecatalogue.repositories

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(genreId: Long, isForce: Boolean): Flow<PagingData<Movie>>
}
