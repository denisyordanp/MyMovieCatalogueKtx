package com.denisyordanp.mymoviecatalogue.usecase

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import kotlinx.coroutines.flow.Flow

fun interface GetMoviesPaging {
    operator fun invoke(genreId: Long): Flow<PagingData<Movie>>
}
