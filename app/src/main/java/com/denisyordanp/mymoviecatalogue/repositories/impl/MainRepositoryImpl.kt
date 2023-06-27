package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.database.MoviesDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MainRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val genreDao: GenresDao,
    private val movieDao: MoviesDao
) : MainRepository {
    override suspend fun reloadGenres() {
        val genres = service.fetchGenres().toEntities()
        genreDao.insertGenres(genres)
    }

    override fun getGenres(): Flow<List<Genre>> {
        return genreDao.getGenres().map { genres ->
            genres.map { genre ->
                genre.toUi()
            }
        }
    }

    override suspend fun reloadMovies(genreId: Int) {
        val movies = service.fetchMovies(genre = genreId).toEntities(genreId)
        movieDao.insertMovies(movies)
    }

    override fun getMovies(genreId: Int): Flow<List<Movie>> {
        return movieDao.getMovies(genreId).map { movies ->
            movies.map { movie ->
                movie.toUi()
            }
        }
    }
}