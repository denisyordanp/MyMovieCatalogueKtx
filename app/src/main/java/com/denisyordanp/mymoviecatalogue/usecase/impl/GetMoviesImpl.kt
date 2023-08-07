package com.denisyordanp.mymoviecatalogue.usecase.impl

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMoviesImpl @Inject constructor(
    private val repository: MovieRepository,
) : GetMovies {
    override fun invoke(genreId: Long?) = if (genreId == null || genreId == 0L) {
        flowOf(PagingData.empty())
    } else {
        repository.getMovies(genreId)
    }
}
