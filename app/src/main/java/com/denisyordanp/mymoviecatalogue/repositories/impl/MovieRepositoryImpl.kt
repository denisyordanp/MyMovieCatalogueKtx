package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.MoviesDao
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MoviesDao,
) : MovieRepository {
    override fun getMovies(genreId: Long) = movieDao.getMovies(genreId)
}
