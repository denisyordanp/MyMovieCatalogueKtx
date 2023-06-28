package com.denisyordanp.mymoviecatalogue.di

import com.denisyordanp.mymoviecatalogue.repositories.GenreRepository
import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.repositories.ReviewsRepository
import com.denisyordanp.mymoviecatalogue.repositories.VideosRepository
import com.denisyordanp.mymoviecatalogue.repositories.impl.GenreRepositoryImpl
import com.denisyordanp.mymoviecatalogue.repositories.impl.MovieDetailRepositoryImpl
import com.denisyordanp.mymoviecatalogue.repositories.impl.MovieRepositoryImpl
import com.denisyordanp.mymoviecatalogue.repositories.impl.ReviewsRepositoryImpl
import com.denisyordanp.mymoviecatalogue.repositories.impl.VideosRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainRepository(
        mainRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    abstract fun bindGenreRepository(
        genreRepositoryImpl: GenreRepositoryImpl
    ): GenreRepository

    @Binds
    abstract fun bindMovieDetailRepository(
        movieDetailRepositoryImpl: MovieDetailRepositoryImpl
    ): MovieDetailRepository

    @Binds
    abstract fun bindReviewsRepository(
        reviewsRepositoryImpl: ReviewsRepositoryImpl
    ): ReviewsRepository

    @Binds
    abstract fun bindVideoRepository(
        videosRepositoryImpl: VideosRepositoryImpl
    ): VideosRepository
}
