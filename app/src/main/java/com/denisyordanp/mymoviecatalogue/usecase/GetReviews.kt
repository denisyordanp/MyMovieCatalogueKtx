package com.denisyordanp.mymoviecatalogue.usecase

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow

fun interface GetReviews {
    operator fun invoke(movieId: Long, isForce: Boolean): Flow<PagingData<Review>>
}