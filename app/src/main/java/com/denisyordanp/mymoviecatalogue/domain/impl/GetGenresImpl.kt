package com.denisyordanp.mymoviecatalogue.domain.impl

import com.denisyordanp.mymoviecatalogue.domain.GetGenres
import com.denisyordanp.mymoviecatalogue.repositories.MainRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresImpl @Inject constructor(
    private val repository: MainRepository
) : GetGenres {
    override fun invoke(isForce: Boolean): Flow<List<Genre>> {
        return flow {
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
}