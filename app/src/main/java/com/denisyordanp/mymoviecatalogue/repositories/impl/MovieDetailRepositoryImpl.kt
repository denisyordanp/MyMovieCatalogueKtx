package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.FavoritesDao
import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.database.MovieDetailDao
import com.denisyordanp.mymoviecatalogue.database.MovieGenreDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val movieDetailDao: MovieDetailDao,
    private val genreDao: GenresDao,
    private val movieGenre: MovieGenreDao,
    private val favoritesDao: FavoritesDao,
) : MovieDetailRepository {
    override suspend fun reloadMovieDetail(movieId: Long) {
        val converted = service.fetchMovieDetail(movieId).toEntity()
        val genres = converted.second
        val movieGenres = genres.map { it.toMovieGenre(movieId) }

        movieDetailDao.insertMovieDetail(converted.first)
        genreDao.insertGenres(converted.second)
        movieGenre.insertMovieGenres(movieGenres)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMovieDetail(movieId: Long): Flow<MovieDetail?> {
        return movieDetailDao.getMovieDetail(movieId = movieId).flatMapConcat { detail ->
            movieGenre.getMovieGenres(movieId).flatMapConcat { movieGenres ->
                val genres = movieGenres.map { it.genre }
                favoritesDao.getFavorite(movieId).map { favorite ->
                    detail?.toUi(genres = genres, isFavorite = favorite != null)
                }
            }
        }
    }
}
