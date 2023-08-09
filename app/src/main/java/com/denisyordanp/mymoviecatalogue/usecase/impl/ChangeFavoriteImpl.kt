package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Favorite
import com.denisyordanp.mymoviecatalogue.usecase.ChangeFavorite
import javax.inject.Inject

class ChangeFavoriteImpl @Inject constructor(
    private val repository: FavoriteRepository
) : ChangeFavorite {
    override suspend fun invoke(favorite: Favorite) {
        val isExist = repository.isExist(favorite.toDb())
        if (isExist) {
            repository.remove(favorite.id)
        } else {
            repository.add(favorite.toDb())
        }
    }
}
