package com.denisyordanp.mymoviecatalogue.repositories

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
    suspend fun reloadReviews(movieId: Long)
    fun getReviews(movieId: Long, isForce: Boolean): Flow<PagingData<Review>>
}
