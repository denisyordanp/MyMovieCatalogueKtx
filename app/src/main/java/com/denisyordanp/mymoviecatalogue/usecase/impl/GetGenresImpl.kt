package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.denisyordanp.mymoviecatalogue.repositories.GenreRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresImpl @Inject constructor(
    private val repository: GenreRepository
) : GetGenres {
    override fun invoke(isForce: Boolean) = flow {
        if (isForce) {
            repository.reloadGenres()
        }
        repository.getGenres()
            .collect {
                if (it.isEmpty()) {
                    repository.reloadGenres()
                }
                emit(it)
            }
    }
}
