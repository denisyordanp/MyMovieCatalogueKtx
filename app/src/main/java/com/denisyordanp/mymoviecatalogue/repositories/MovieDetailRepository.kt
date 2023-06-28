package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun reloadMovieDetail(movieId: Long)
    fun getMovieDetail(movieId: Long): Flow<MovieDetail?>
}
