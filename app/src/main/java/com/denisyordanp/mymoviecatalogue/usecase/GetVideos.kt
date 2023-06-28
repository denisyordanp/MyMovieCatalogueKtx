package com.denisyordanp.mymoviecatalogue.usecase

import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import kotlinx.coroutines.flow.Flow

fun interface GetVideos {
    operator fun invoke(movieId: Long, isForce: Boolean): Flow<List<Video>>
}