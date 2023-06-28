package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import kotlinx.coroutines.flow.Flow

interface VideosRepository {
    suspend fun reloadVideos(movieId: Long)
    fun getVideos(movieId: Long): Flow<List<Video>>
}
