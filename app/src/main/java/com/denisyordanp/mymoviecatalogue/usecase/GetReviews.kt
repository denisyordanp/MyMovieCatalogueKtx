package com.denisyordanp.mymoviecatalogue.usecase

import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow

fun interface GetReviews {
    operator fun invoke(movieId: Int, isForce: Boolean): Flow<List<Review>>
}