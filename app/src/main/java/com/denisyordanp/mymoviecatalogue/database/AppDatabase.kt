package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie

@Database(
    entities = [
        Genre::class,
        Movie::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenresDao
    abstract fun movieDao(): MoviesDao

    companion object {
        const val DB_NAME = "my_movie_catalogue"
    }
}

