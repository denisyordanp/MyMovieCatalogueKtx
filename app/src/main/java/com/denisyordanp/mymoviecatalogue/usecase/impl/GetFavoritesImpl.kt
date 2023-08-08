package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetFavorites
import javax.inject.Inject

class GetFavoritesImpl @Inject constructor(
    private val repository: FavoriteRepository
) : GetFavorites {
    override fun invoke() = repository.getFavorites()
}
