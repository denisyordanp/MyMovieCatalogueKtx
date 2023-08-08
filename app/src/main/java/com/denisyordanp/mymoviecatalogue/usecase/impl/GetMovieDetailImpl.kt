package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetMovieDetailImpl @Inject constructor(
    private val repository: MovieDetailRepository
) : GetMovieDetail {
    override fun invoke(movieId: Long, isForce: Boolean) = flow {
        repository.getMovieDetail(movieId)
            .onStart {
                if (isForce) repository.reloadMovieDetail(movieId)
            }
            .collect {
                if (it == null) {
                    repository.reloadMovieDetail(movieId)
                } else {
                    emit(it)
                }
            }
    }
}
