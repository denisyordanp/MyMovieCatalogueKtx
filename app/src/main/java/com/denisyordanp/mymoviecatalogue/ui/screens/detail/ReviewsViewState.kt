package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ReviewsViewState(
    val reviews: Flow<PagingData<Review>>,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        fun idle() = ReviewsViewState(
            reviews = emptyFlow(),
            isLoading = false,
            error = null
        )
    }
}
