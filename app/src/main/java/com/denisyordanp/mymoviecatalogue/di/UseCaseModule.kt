package com.denisyordanp.mymoviecatalogue.di

import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import com.denisyordanp.mymoviecatalogue.usecase.impl.GetGenresImpl
import com.denisyordanp.mymoviecatalogue.usecase.impl.GetMoviesImpl
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
}
