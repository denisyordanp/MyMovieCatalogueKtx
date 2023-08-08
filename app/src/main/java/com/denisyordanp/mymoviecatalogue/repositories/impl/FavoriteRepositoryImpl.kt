package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.FavoritesDao
import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.denisyordanp.mymoviecatalogue.schemas.database.Favorite
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoriteRepository {
    override fun getFavorites() = favoritesDao.getFavorites()

    override suspend fun isExist(movie: Favorite): Boolean {
        return favoritesDao.getFavorite(movieId = movie.id).first() != null
    }

    override suspend fun add(movie: Favorite) {
        favoritesDao.insertFavorite(movie)
    }

    override suspend fun remove(id: Long) {
        favoritesDao.removeMovie(id)
    }
}
