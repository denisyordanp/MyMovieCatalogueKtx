package com.denisyordanp.mymoviecatalogue.ui.screens.main

import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie

data class MovieViewState(
    val movies: List<Movie>,
    val error: Throwable?,
    val isLoading: Boolean
) {
    companion object {
        fun idle() = MovieViewState(
            movies = emptyList(),
            error = null,
            isLoading = false
        )
    }
}
