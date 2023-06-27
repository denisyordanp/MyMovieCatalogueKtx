package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun reloadGenres()
    fun getGenres(): Flow<List<Genre>>
}