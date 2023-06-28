package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovies {
    override fun invoke(genreId: Long, isForce: Boolean) = flow {
        if (isForce) {
            repository.reloadMovies(genreId)
        }
        repository.getMovies(genreId)
            .collect {
                if (it.isEmpty()) {
                    repository.reloadMovies(genreId)
                } else {
                    emit(it)
                }
            }
    }
}
