package com.denisyordanp.mymoviecatalogue.ui.screens.main

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre

data class MainViewState(
    val selectedGenre: Genre?,
    val error: Throwable?,
    val genreViewState: GenreViewState,
    val movieViewState: MovieViewState
) {

    val isLoading: Boolean = movieViewState.isLoading || genreViewState.isLoading

    companion object {
        fun idle() = MainViewState(
            genreViewState = GenreViewState.idle(),
            movieViewState = MovieViewState.idle(),
            selectedGenre = null,
            error = null,
        )
    }
}
