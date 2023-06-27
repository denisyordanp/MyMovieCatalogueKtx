package com.denisyordanp.mymoviecatalogue.usecase

import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow

fun interface GetMovies {
    operator fun invoke(genreId: Int, isForce: Boolean): Flow<List<Movie>>
}
