package com.denisyordanp.mymoviecatalogue.usecase

import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import kotlinx.coroutines.flow.Flow

fun interface GetMovieDetail {
    operator fun invoke(movieId: Int, isForce: Boolean): Flow<MovieDetail>
}