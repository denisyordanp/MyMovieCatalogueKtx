package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.ReviewsDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.ReviewsRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val reviewsDao: ReviewsDao,
) : ReviewsRepository {
    override suspend fun reloadReviews(movieId: Int) {
        val reviews = service.fetchReviews(movieId).toEntity(movieId)

        reviewsDao.insertReviews(reviews)
    }

    override fun getReviews(movieId: Int): Flow<List<Review>> {
        return reviewsDao.getReviews(movieId).map { reviews ->
            reviews.map { review ->
                review.toUi()
            }
        }
    }
}