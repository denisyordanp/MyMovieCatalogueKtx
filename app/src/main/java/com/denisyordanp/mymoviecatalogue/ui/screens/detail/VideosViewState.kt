package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import com.denisyordanp.mymoviecatalogue.schemas.ui.Video

data class VideosViewState(
    val videos: List<Video>,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        fun idle() = VideosViewState(
            videos = emptyList(),
            isLoading = false,
            error = null
        )
    }
}
