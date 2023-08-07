package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MainViewState(
    val selectedGenre: Genre?,
    val error: Throwable?,
    val genres: List<Genre>,
    val isLoading: Boolean,
    val movies: Flow<PagingData<Movie>>,
) {
    companion object {
        fun idle() = MainViewState(
            selectedGenre = null,
            error = null,
            movies = emptyFlow(),
            genres = emptyList(),
            isLoading = false
        )
    }
}
