package com.denisyordanp.mymoviecatalogue.ui.screens.main

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre

data class GenreViewState(
    val genres: List<Genre>,
    val error: Throwable?,
    val isLoading: Boolean
) {
    companion object {
        fun idle() = GenreViewState(
            genres = emptyList(),
            error = null,
            isLoading = false
        )
    }
}
