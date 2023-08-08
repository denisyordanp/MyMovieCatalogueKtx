package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.FavoritesDao
import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Favorite
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoriteRepository {
    override fun getFavorites() = favoritesDao.getFavorites()
        .map { favorites -> favorites.map { it.toUi() } }

    override suspend fun changeFavorite(movie: Favorite) {
        val isExist = favoritesDao.getFavorite(movieId = movie.id).first() != null
        if (isExist) {
            favoritesDao.removeMovie(movie.id)
        } else {
            favoritesDao.insertFavorite(movie.toDb())
        }
    }
}
