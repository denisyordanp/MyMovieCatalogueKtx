package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.denisyordanp.mymoviecatalogue.repositories.GenreRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetGenresImpl @Inject constructor(
    private val repository: GenreRepository
) : GetGenres {
    override fun invoke(isForce: Boolean) = flow {
        repository.getGenres()
            .onStart {
                if (isForce) repository.reloadGenres()
            }
            .collect {
                if (it.isEmpty()) {
                    repository.reloadGenres()
                } else {
                    emit(it)
                }
            }
    }
}
