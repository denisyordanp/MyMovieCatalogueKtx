package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.MovieWithGenres
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailDao {

    @Transaction
    @Query("SELECT * FROM '${MovieDetail.TABLE_NAME}' WHERE ${MovieDetail.ID_COLUMN} = :movieId")
    fun getMovieDetail(movieId: Int): Flow<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetail)
}