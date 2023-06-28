package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import com.denisyordanp.mymoviecatalogue.schemas.database.Video
import kotlinx.coroutines.flow.Flow

@Dao
interface VideosDao {

    @Query("SELECT * FROM '${Video.TABLE_NAME}' WHERE ${Movie.ID_COLUMN} = :movieId")
    fun getVideos(movieId: Int): Flow<List<Video>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(reviews: List<Video>)
}