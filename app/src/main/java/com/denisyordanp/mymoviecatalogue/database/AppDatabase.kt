package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.database.Review
import com.denisyordanp.mymoviecatalogue.schemas.database.Video
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.MovieGenre

@Database(
    entities = [
        Genre::class,
        Movie::class,
        MovieDetail::class,
        Review::class,
        Video::class,
        MovieGenre::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenresDao
    abstract fun movieDao(): MoviesDao
    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun reviewsDao(): ReviewsDao
    abstract fun videosDao(): VideosDao
    abstract fun movieGenreDao(): MovieGenreDao

    companion object {
        const val DB_NAME = "my_movie_catalogue"
    }
}

