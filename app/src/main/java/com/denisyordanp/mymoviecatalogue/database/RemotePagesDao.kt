package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.denisyordanp.mymoviecatalogue.schemas.database.RemotePages

@Dao
interface RemotePagesDao {

    @Query("SELECT * FROM '${RemotePages.TABLE_NAME}' WHERE ${RemotePages.LABEL_COLUMN} = :label")
    suspend fun getPages(label: String): RemotePages?

    @Upsert
    suspend fun insertOrUpdate(pages: RemotePages)

}