package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM '${RemoteKeys.TABLE_NAME}' WHERE ${Movie.ID_COLUMN} = :id")
    suspend fun getRemoteKeyByMovieID(id: Long): RemoteKeys?

    @Query("SELECT * FROM '${RemoteKeys.TABLE_NAME}' WHERE ${Genre.ID_COLUMN} = :id")
    suspend fun getRemoteKeyByGenreId(id: Long): List<RemoteKeys>?

    @Query("DELETE FROM '${RemoteKeys.TABLE_NAME}' WHERE ${Genre.ID_COLUMN} = :id")
    suspend fun clearRemoteKeys(id: Long)

    @Query("SELECT ${RemoteKeys.CREATED_AT_COLUMN} FROM '${RemoteKeys.TABLE_NAME}' ORDER BY ${RemoteKeys.CREATED_AT_COLUMN} DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
