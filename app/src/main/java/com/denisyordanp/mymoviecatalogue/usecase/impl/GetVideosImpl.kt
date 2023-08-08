package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.VideosRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetVideos
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVideosImpl @Inject constructor(
    private val repository: VideosRepository
) : GetVideos {
    override fun invoke(movieId: Long, isForce: Boolean) = flow {
        if (isForce) {
            repository.reloadVideos(movieId)
        }

        val videosFlow = repository.getVideos(movieId)
        val initial = videosFlow.first()

        if (initial.isEmpty()) {
            repository.reloadVideos(movieId)
        }

        videosFlow.collect(this)
    }
}
