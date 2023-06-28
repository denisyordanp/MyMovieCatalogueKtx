package com.denisyordanp.mymoviecatalogue.repositories.impl

import android.util.Log
import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.database.MovieDetailDao
import com.denisyordanp.mymoviecatalogue.database.MovieGenreDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val movieDetailDao: MovieDetailDao,
    private val genreDao: GenresDao,
    private val movieGenre: MovieGenreDao,
) : MovieDetailRepository {
    override suspend fun reloadMovieDetail(movieId: Long) {
        val converted = service.fetchMovieDetail(movieId).toEntity(movieId)
        val genres = converted.second
        val movieGenres = genres.map { it.toMovieGenre(movieId) }

        Log.d("TESTING", "conv: $converted")
        movieDetailDao.insertMovieDetail(converted.first)
        genreDao.insertGenres(converted.second)
        movieGenre.insertMovieGenres(movieGenres)
    }

    override fun getMovieDetail(movieId: Long): Flow<MovieDetail?> {
        return movieDetailDao.getMovieDetail(movieId)
            .map {
            Log.d("TESTING", "detail: $it")
                it?.toUi()
            }
    }
}