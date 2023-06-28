package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
    suspend fun reloadReviews(movieId: Long)
    fun getReviews(movieId: Long): Flow<List<Review>>
}
