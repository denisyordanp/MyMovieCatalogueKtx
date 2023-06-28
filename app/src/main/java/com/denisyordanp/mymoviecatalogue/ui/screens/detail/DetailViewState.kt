package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail

data class DetailViewState(
    val movieDetail: MovieDetail?,
    val isLoading: Boolean,
    val error: Throwable?,
    val reviewsViewState: ReviewsViewState,
    val videosViewState: VideosViewState
) {
    companion object {
        fun idle() = DetailViewState(
            movieDetail = null,
            isLoading = false,
            error = null,
            reviewsViewState = ReviewsViewState.idle(),
            videosViewState = VideosViewState.idle()
        )
    }
}
