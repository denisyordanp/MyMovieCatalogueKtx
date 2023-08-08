package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailImpl @Inject constructor(
    private val repository: MovieDetailRepository,
) : GetMovieDetail {
    override fun invoke(movieId: Long, isForce: Boolean) = flow {
        val detailFlow = repository.getMovieDetail(movieId)
        val initial = detailFlow.first()

        if (initial == null || isForce) {
            repository.reloadMovieDetail(movieId)
        }

        detailFlow.collect(this)
    }
}
