package com.denisyordanp.mymoviecatalogue.domain.impl

import com.denisyordanp.mymoviecatalogue.domain.GetGenres
import com.denisyordanp.mymoviecatalogue.repositories.MainRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresImpl @Inject constructor(
    private val repository: MainRepository
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