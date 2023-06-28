package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailImpl @Inject constructor(
    private val repository: MovieDetailRepository
) : GetMovieDetail {
    override fun invoke(movieId: Int, isForce: Boolean) = flow {
        if (isForce) {
            repository.reloadMovieDetail(movieId)
        }
        repository.getMovieDetail(movieId)
            .collect {
                if (it == null) {
                    repository.reloadMovieDetail(movieId)
                } else {
                    emit(it)
                }
            }
    }
}