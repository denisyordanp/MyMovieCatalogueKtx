package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.database.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun isExist(movie: Favorite): Boolean
    suspend fun add(movie: Favorite)
    suspend fun remove(id: Long)
}
