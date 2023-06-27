package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {

    @Query("SELECT * FROM '${Genre.TABLE_NAME}'")
    fun getGenres(): Flow<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<Genre>)

}