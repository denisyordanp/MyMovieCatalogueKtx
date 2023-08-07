package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.paging.PagingData
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DetailViewState(
    val movieDetail: MovieDetail?,
    val isLoading: Boolean,
    val error: Throwable?,
    val reviews: Flow<PagingData<Review>>,
    val videosViewState: VideosViewState
) {
    fun getIsLoading(): Boolean = isLoading || videosViewState.isLoading
    companion object {
        fun idle() = DetailViewState(
            movieDetail = null,
            isLoading = false,
            error = null,
            reviews = emptyFlow(),
            videosViewState = VideosViewState.idle()
        )
    }
}
