package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
    suspend fun reloadReviews(movieId: Int)
    fun getReviews(movieId: Int): Flow<List<Review>>
}
