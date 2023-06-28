package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.MovieGenre
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.MovieWithGenres
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieGenreDao {
    @Query("SELECT * FROM '${MovieGenre.TABLE_NAME}' WHERE ${MovieDetail.ID_COLUMN} = :movieId")
    fun getMovieGenres(movieId: Long): Flow<List<MovieWithGenres>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movieGenres: List<MovieGenre>)
}