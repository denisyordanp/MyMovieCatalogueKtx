package com.denisyordanp.mymoviecatalogue.usecase.impl

import androidx.paging.map
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesImpl @Inject constructor(
    private val repository: MovieRepository,
) : GetMovies {
    override fun invoke(genreId: Long, isForce: Boolean) = repository.getMovies(genreId, isForce)
        .map {
            it.map { movie ->
                movie.toUi()
            }
        }
}
