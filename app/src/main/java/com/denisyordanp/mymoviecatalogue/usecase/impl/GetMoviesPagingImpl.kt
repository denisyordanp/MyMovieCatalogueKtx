package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetMoviesPaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMoviesPagingImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMoviesPaging {
    override fun invoke(genreId: Long) = repository.getMoviesPager(genreId)
        .flow.flowOn(Dispatchers.IO)
}