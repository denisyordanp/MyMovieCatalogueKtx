package com.denisyordanp.mymoviecatalogue.usecase

import com.denisyordanp.mymoviecatalogue.schemas.ui.Favorite
import kotlinx.coroutines.flow.Flow

fun interface GetFavorites {
    operator fun invoke(): Flow<List<Favorite>>
}
