package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.denisyordanp.mymoviecatalogue.schemas.database.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM '${Favorite.TABLE_NAME}'")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM '${Favorite.TABLE_NAME}' WHERE ${Favorite.ID_COLUMN} = :movieId")
    fun getFavorite(movieId: Long): Flow<Favorite?>

    @Query("DELETE FROM '${Favorite.TABLE_NAME}' WHERE ${Favorite.ID_COLUMN} = :movieId")
    suspend fun removeMovie(movieId: Long)

    @Upsert
    suspend fun insertFavorite(movie: Favorite)
}
