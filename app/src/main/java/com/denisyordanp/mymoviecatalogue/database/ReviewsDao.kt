package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.database.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewsDao {

    @Query("SELECT * FROM '${Review.TABLE_NAME}' WHERE ${MovieDetail.ID_COLUMN} = :movieId")
    fun getReviews(movieId: Long): Flow<List<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)
}