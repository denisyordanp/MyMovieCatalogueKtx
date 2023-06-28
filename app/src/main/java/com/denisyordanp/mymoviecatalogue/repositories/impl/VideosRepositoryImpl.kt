package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.VideosDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.VideosRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import com.denisyordanp.mymoviecatalogue.tools.AppConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val videosDao: VideosDao,
) : VideosRepository {
    override suspend fun reloadVideos(movieId: Long) {
        val videos = service.fetchVideos(movieId)
            .toEntity(movieId)
            .take(AppConfig.MAX_VIDEOS)

        videosDao.insertVideos(videos)
    }

    override fun getVideos(movieId: Long): Flow<List<Video>> {
        return videosDao.getVideos(movieId).map { videos ->
            videos.map { video ->
                video.toUi()
            }
        }
    }
}