package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.ReviewsRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetReviews
import javax.inject.Inject

class GetReviewsImpl @Inject constructor(
    private val repository: ReviewsRepository
) : GetReviews {
    override fun invoke(movieId: Long, isForce: Boolean) = repository.getReviews(movieId, isForce)
}