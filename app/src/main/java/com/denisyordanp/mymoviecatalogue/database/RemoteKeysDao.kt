package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From '${RemoteKeys.TABLE_NAME}' Where ${RemoteKeys.MOVIE_ID_COLUMN} = :id")
    suspend fun getRemoteKeyByMovieID(id: Long): RemoteKeys?

    @Query("Delete From remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select ${RemoteKeys.CREATED_AT_COLUMN} From '${RemoteKeys.TABLE_NAME}' Order By ${RemoteKeys.CREATED_AT_COLUMN} DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}