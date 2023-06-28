package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import com.denisyordanp.mymoviecatalogue.schemas.ui.Review

data class ReviewsViewState(
    val reviews: List<Review>,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        fun idle() = ReviewsViewState(
            reviews = emptyList(),
            isLoading = false,
            error = null
        )
    }
}
