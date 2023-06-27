package com.denisyordanp.mymoviecatalogue.di

import com.denisyordanp.mymoviecatalogue.domain.GetGenres
import com.denisyordanp.mymoviecatalogue.domain.impl.GetGenresImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetGenres(
        getGenresImpl: GetGenresImpl
    ): GetGenres
}
