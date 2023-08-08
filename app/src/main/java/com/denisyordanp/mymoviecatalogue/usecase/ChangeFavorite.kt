package com.denisyordanp.mymoviecatalogue.usecase

import com.denisyordanp.mymoviecatalogue.schemas.ui.Favorite

fun interface ChangeFavorite {
    suspend operator fun invoke(favorite: Favorite)
}
