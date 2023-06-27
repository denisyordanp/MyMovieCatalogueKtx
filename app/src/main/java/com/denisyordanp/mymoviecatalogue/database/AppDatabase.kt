package com.denisyordanp.mymoviecatalogue.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre

@Database(
    entities = [
        Genre::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenresDao

    companion object {
        const val DB_NAME = "solite_db"
    }
}

