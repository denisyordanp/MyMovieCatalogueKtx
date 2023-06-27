package com.denisyordanp.mymoviecatalogue.ui.screens.main

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie

data class MainViewState(
    val genres: List<Genre>,
    val movies: List<Movie>,
    val selectedGenre: Genre?,
    val error: Throwable?,
    val isLoading: Boolean
) {
    companion object {
        fun idle() = MainViewState(
            genres = emptyList(),
            movies = emptyList(),
            selectedGenre = null,
            error = null,
            isLoading = false
        )
    }
}
