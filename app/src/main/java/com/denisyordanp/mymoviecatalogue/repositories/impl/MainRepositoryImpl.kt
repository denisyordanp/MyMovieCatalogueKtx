package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MainRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val dao: GenresDao
) : MainRepository {
    override suspend fun reloadGenres() {
        val genres = service.fetchGenres().toEntities()
        dao.insertGenres(genres)
    }

    override fun getGenres(): Flow<List<Genre>> {
        return dao.getGenres().map { genres ->
            genres.map { genre ->
                genre.toUi()
            }
        }
    }
}