package com.denisyordanp.mymoviecatalogue.repositories.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.denisyordanp.mymoviecatalogue.database.ReviewsDao
import com.denisyordanp.mymoviecatalogue.mediator.ReviewPagingSource
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.ReviewsRepository
import com.denisyordanp.mymoviecatalogue.schemas.response.Movies
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val reviewsDao: ReviewsDao,
) : ReviewsRepository {
    override suspend fun reloadReviews(movieId: Long) {
        val reviews = service.fetchReviews(movieId).toEntity(movieId)

        reviewsDao.insertReviews(reviews)
    }

    override fun getReviews(movieId: Long, isForce: Boolean) = Pager(
        config = PagingConfig(pageSize = Movies.PAGE_SIZE),
        pagingSourceFactory = {
            ReviewPagingSource(service, movieId, isForce)
        }
    ).flow
}