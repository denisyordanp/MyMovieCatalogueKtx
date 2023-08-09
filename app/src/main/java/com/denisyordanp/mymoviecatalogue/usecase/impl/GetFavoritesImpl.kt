package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetFavorites
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoritesImpl @Inject constructor(
    private val repository: FavoriteRepository
) : GetFavorites {
    override fun invoke() = repository.getFavorites().map { favorites ->
        favorites.map { it.toUi() }
    }
}
