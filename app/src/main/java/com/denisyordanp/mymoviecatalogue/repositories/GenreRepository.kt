package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun reloadGenres()
    fun getGenres(): Flow<List<Genre>>
}
