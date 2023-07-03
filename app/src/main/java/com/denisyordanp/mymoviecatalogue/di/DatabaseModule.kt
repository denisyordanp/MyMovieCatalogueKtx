package com.denisyordanp.mymoviecatalogue.di

import android.content.Context
import androidx.room.Room
import com.denisyordanp.mymoviecatalogue.database.AppDatabase
import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.database.MovieDetailDao
import com.denisyordanp.mymoviecatalogue.database.MovieGenreDao
import com.denisyordanp.mymoviecatalogue.database.MoviesDao
import com.denisyordanp.mymoviecatalogue.database.ReviewsDao
import com.denisyordanp.mymoviecatalogue.database.VideosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideGenreDao(appDatabase: AppDatabase): GenresDao = appDatabase.genreDao()

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MoviesDao = appDatabase.movieDao()

    @Provides
    fun provideMovieDetailDao(appDatabase: AppDatabase): MovieDetailDao = appDatabase.movieDetailDao()

    @Provides
    fun provideMovieGenreDao(appDatabase: AppDatabase): MovieGenreDao = appDatabase.movieGenreDao()

    @Provides
    fun provideReviewsDao(appDatabase: AppDatabase): ReviewsDao = appDatabase.reviewsDao()

    @Provides
    fun provideVideosDao(appDatabase: AppDatabase): VideosDao = appDatabase.videosDao()
}
