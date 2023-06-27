package com.denisyordanp.mymoviecatalogue.domain.impl

import com.denisyordanp.mymoviecatalogue.domain.GetMovies
import com.denisyordanp.mymoviecatalogue.repositories.MainRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesImpl @Inject constructor(
    private val repository: MainRepository
) : GetMovies {
    override fun invoke(genreId: Int, isForce: Boolean) = flow {
        if (isForce) {
            repository.reloadMovies(genreId)
        }
        repository.getMovies(genreId)
            .collect {
                if (it.isEmpty()) {
                    repository.reloadMovies(genreId)
                }
                emit(it)
            }
    }
}