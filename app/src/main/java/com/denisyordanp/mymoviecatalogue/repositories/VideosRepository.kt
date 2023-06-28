package com.denisyordanp.mymoviecatalogue.repositories

import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import kotlinx.coroutines.flow.Flow

interface VideosRepository {
    suspend fun reloadVideos(movieId: Int)
    fun getVideos(movieId: Int): Flow<List<Video>>
}
