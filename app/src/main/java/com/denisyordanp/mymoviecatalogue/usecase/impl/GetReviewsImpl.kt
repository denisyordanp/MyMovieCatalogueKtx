package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.ReviewsRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetReviews
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetReviewsImpl @Inject constructor(
    private val repository: ReviewsRepository
) : GetReviews {
    override fun invoke(movieId: Long, isForce: Boolean) = flow {
        var hasReload = false

        if (isForce) {
            repository.reloadReviews(movieId)
            hasReload = true
        }
        repository.getReviews(movieId)
            .collect {
                if (it.isEmpty() && hasReload.not()) {
                    repository.reloadReviews(movieId)
                    hasReload = true
                }
                emit(it)
            }
    }
}