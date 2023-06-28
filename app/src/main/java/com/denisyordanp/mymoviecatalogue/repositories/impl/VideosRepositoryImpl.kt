package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.VideosDao
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.VideosRepository
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val videosDao: VideosDao,
) : VideosRepository {
    override suspend fun reloadVideos(movieId: Int) {
        val videos = service.fetchVideos(movieId).toEntity(movieId)

        videosDao.insertVideos(videos)
    }

    override fun getVideos(movieId: Int): Flow<List<Video>> {
        return videosDao.getVideos(movieId).map { videos ->
            videos.map { video ->
                video.toUi()
            }
        }
    }
}