package com.denisyordanp.mymoviecatalogue.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM '${Movie.TABLE_NAME}' WHERE ${Genre.ID_COLUMN} = :genreId")
    fun getMovies(genreId: Long): Flow<List<Movie>>

    @Query("SELECT * FROM '${Movie.TABLE_NAME}' WHERE ${Genre.ID_COLUMN} = :genreId ORDER BY ${Movie.ID_COLUMN}")
    fun getMoviesPaging(genreId: Long): PagingSource<Int, Movie>

    @Query("DELETE FROM '${Movie.TABLE_NAME}' WHERE ${Genre.ID_COLUMN} = :genreId")
    suspend fun clearAll(genreId: Long)

    @Upsert
    suspend fun insertMovies(movies: List<Movie>)
}