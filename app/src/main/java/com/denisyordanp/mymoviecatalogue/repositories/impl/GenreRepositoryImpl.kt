package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.GenreRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val genreDao: GenresDao,
) : GenreRepository {
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
}
