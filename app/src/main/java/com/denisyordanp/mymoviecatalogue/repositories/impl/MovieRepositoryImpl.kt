package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.MoviesDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val movieDao: MoviesDao
) : MovieRepository {

    override suspend fun reloadMovies(genreId: Long) {
        val movies = service.fetchMovies(genre = genreId).toEntities(genreId)
        movieDao.insertMovies(movies)
    }

    override fun getMovies(genreId: Long): Flow<List<Movie>> {
        return movieDao.getMovies(genreId).map { movies ->
            movies.map { movie ->
                movie.toUi()
            }
        }
    }
}
