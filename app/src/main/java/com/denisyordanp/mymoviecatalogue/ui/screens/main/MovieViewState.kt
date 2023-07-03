package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieViewState(
    val movies: Flow<PagingData<Movie>>,
    val error: Throwable?,
    val isLoading: Boolean
) {
    companion object {
        fun idle() = MovieViewState(
            movies = emptyFlow(),
            error = null,
            isLoading = false
        )
    }
}
