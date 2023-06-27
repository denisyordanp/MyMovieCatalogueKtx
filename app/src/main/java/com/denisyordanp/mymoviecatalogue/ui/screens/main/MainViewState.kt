package com.denisyordanp.mymoviecatalogue.ui.screens.main

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre

data class MainViewState(
    val genres: List<Genre>,
    val selectedGenre: Genre?,
    val error: Throwable?,
    val isLoading: Boolean
) {
    companion object {
        fun idle() = MainViewState(
            genres = emptyList(),
            selectedGenre = null,
            error = null,
            isLoading = false
        )
    }
}
