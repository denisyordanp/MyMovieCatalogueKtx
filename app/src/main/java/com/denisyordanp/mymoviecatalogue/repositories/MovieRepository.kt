package com.denisyordanp.mymoviecatalogue.repositories

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(genreId: Long): Flow<PagingData<Movie>>
}
