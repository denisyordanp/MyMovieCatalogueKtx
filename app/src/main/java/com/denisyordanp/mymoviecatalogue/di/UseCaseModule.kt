package com.denisyordanp.mymoviecatalogue.di

import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import com.denisyordanp.mymoviecatalogue.usecase.GetReviews
import com.denisyordanp.mymoviecatalogue.usecase.GetVideos
import com.denisyordanp.mymoviecatalogue.usecase.impl.GetGenresImpl
import com.denisyordanp.mymoviecatalogue.usecase.impl.GetMovieDetailImpl
import com.denisyordanp.mymoviecatalogue.usecase.impl.GetMoviesImpl
import com.denisyordanp.mymoviecatalogue.usecase.impl.GetReviewsImpl
import com.denisyordanp.mymoviecatalogue.usecase.impl.GetVideosImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetGenres(
        getGenresImpl: GetGenresImpl
    ): GetGenres

    @Binds
    abstract fun bindGetMovies(
        getMoviesImpl: GetMoviesImpl
    ): GetMovies

    @Binds
    abstract fun bindGetMovieDetail(
        getMovieDetailImpl: GetMovieDetailImpl
    ): GetMovieDetail

    @Binds
    abstract fun bindGetReviews(
        getReviewsImpl: GetReviewsImpl
    ): GetReviews

    @Binds
    abstract fun bindGetVideos(
        getVideosImpl: GetVideosImpl
    ): GetVideos
}
